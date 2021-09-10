#
#  Copyright 2014+ Carnegie Mellon University
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#
import torch
import inspect

from flexneuart.config import DEVICE_CPU
from flexneuart.models import model_registry

MODEL_PARAM_PREF = 'model.'
MODEL_ARGS = 'model_args'
MODEL_STATE_DICT = 'model_weights'
MAX_QUERY_LEN = 'max_query_len'
MAX_DOC_LEN = 'max_doc_len'


def get_model_param_dict(args, model_class):
    """This function iterates over the list of arguments starting with model.
       and checks if the model constructor supports them. It creates
       a parameter dictionary that can be used to initialize the model.

       If the arguments object contains extra model parameters, an exception is
       thrown.

       Limitation: it supports only regular arguments.
    """
    param_dict = {}
    model_init_args = inspect.signature(model_class).parameters.keys()

    for param_name, param_val in vars(args).items():
        if param_name.startswith(MODEL_PARAM_PREF):
            param_name = param_name[len(MODEL_PARAM_PREF):]
            if param_name in model_init_args:
                param_dict[param_name] = param_val
            else:
                raise Exception(f'{model_class} does not have parameter {param_name}, but it is provided via arguments!')

    return param_dict


class ModelSerializer:
    """
        1. Our models can be arbitrary: Not just BERT-based models or models from
              the HuggingFace repository.
        2. However, we need to load/save them nonetheless.
        3. Saving only model weights is inconvenient as it does not preserve
              model parameters and objects (such as vocabularies)
              passed to the model constructor.
        4. Saving complete models is also bad, because loading models requires
              the presence of the model code in the path.
        5. At the same time HuggingFace function from_pretrained() works only for
              HuggingFace models.

        A solution to these woes is as follows:

        1. Use a helper model save/restore wrapper object that saves model init parameters
              and constructs a model.
        2. It serialized both the model weights and constructor parameters.
        3. One tricky part is saving objects that a model constructor would load.
            We cannot  let the model do this directly.

          Instead, we require a model to implement a pre-constructor (pre_init) function
          that takes all the parameters an processes them as necessary. By default,
          it just returns the unmodified dictionary of parameters, which it accepts
          as input.

    """
    def __init__(self, model_class=None, model_name=None):
        """Constructor that accepts either the model class or model name.

        :param model_class:
        :param model_name:
        """
        if model_class is None:
            assert model_name is not None, 'No model class or name is specified!'

            model_class = model_registry.registered.get(model_name, default=None)
            if model_class is None:
                raise Exception(f'Model name {model_name} is not defined!')

        self.model_class = model_class
        self.params = {}
        self.model = None

    def create_model_from_args(self, args):
        """Create a model using a set arguments.

           Maximum query/document lengths need to be present among arguments.

        :param args:  parsed command-line arguments (possibly overridden from JSON config).

        :return:
        """
        max_query_len = getattr(args, 'max_query_len')
        max_doc_len = getattr(args, 'max_doc_len')

        model_args_orig = get_model_param_dict(args, self.model_class)
        self.model_args_processed = self.model_class.pre_init(model_args_orig)

        self.model = self.model_class(**self.model_args_processed)
        self.max_query_len = max_query_len
        self.max_doc_len = max_doc_len

    def save_all(self, file_name):
        torch.save({MODEL_ARGS : self.model,
                    MODEL_STATE_DICT : self.model.state_dict(),
                    MAX_QUERY_LEN : self.max_query_len,
                    MAX_DOC_LEN : self.max_doc_len},
                   file_name)

    def load_all(self, file_name):
        """Load the previous version saved using the save_all function."""
        data = torch.load(file_name, map_location=DEVICE_CPU)
        for exp_key in [MODEL_ARGS, MODEL_STATE_DICT, MAX_QUERY_LEN, MAX_DOC_LEN]:
            if not exp_key in data:
                raise Exception(f'Missing key {exp_key} in {file_name}')

        self.model_args_processed = data[MODEL_ARGS]
        self.model = self.model_class(**self.model_args_processed)
        self.model.load_state_dict(data[MODEL_STATE_DICT], strict=True)
        self.max_query_len = data[MAX_QUERY_LEN]
        self.max_doc_len = data[MAX_DOC_LEN]

    def load_weights(self, file_name, strict=False):
        """Load only weights."""
        assert self.model is not None, "The model needs to be created using 'create_model_from_args'"
        weights = torch.load(file_name, map_location=DEVICE_CPU)

        self.model.load_state_dict(weights, strict=strict)


"""The base class for *ALL* models."""
class BaseModel(torch.nn.Module):

    def __init__(self):
        super().__init__()

    def pre_init(self, model_param_dict):
        """a pre-constructor (pre_init) function
          that takes all the parameters an processes them as necessary. By default,
          it just returns the unmodified parameter dictionary.

        :param model_param_dict:
        :return:
        """
        raise model_param_dict

    def tokenize_and_encode(self, text):
        """Tokenizes the text and converts tokens to respective IDs

        :param text:  input text
        :return:      an array of token IDs
        """
        raise NotImplementedError





