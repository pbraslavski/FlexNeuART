#!/usr/bin/env python
#
#  Copyright 2014+ Carnegie Mellon University
#
#  Using some bits from CEDR: https://github.com/Georgetown-IR-Lab/cedr
#  which has MIT, i.e., Apache 2 compatible license.
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

"""
   A simple script to count the number of parameters in the saved FlexNeuART model.
"""
import argparse
from flexneuart.models.base import ModelSerializer

parser = argparse.ArgumentParser('model training and validation')

parser.add_argument('--input', metavar='input model file',
                    type=str,
                    required=True, help='input model file previously generated by the framework')

def count_parameters(model, requires_grad=True):
    if requires_grad:
        grad_val = [True]
    else:
        grad_val = [True, False]

    return sum(p.numel() for p in model.parameters() if p.requires_grad in grad_val)

args = parser.parse_args()

model_holder : ModelSerializer = ModelSerializer.load_all(args.input)

print('# of parameters', count_parameters(model_holder.model))