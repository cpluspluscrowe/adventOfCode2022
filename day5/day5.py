#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import pandas as pd
import re

s = open("./input.txt", "r").read()


parts = s.split("\n\n")
boxes = parts[0].split("\n")[-1].split()
directions = parts[1].split("\n")[:-1]
replaced_stacks = list(map(lambda x: x.replace("    ","[-]").replace(" ","").replace("[","").replace("]","") , parts[0].split("\n")[:-1]))
stacks = list(map(lambda x: [t for t in x], replaced_stacks))

df = pd.DataFrame(stacks)

stacks = []
for column in df:
    stack = list(filter(lambda x: x != "-", list(df[column])))
    stack.reverse()
    stacks.append(stack)

# stacks has my stacks

class Direction():
    def __init__(self, number, orig, dest):
        self.how_many = number
        self.original_stack = orig
        self.destination_stack = dest
    def __str__(self):
        return str((self.how_many, self.original_stack, self.destination_stack))
    

pattern = "move (\d+) from (\d+) to (\d+)"
for direction in directions:
    result = re.match(pattern, direction)
    direction = Direction(int(result.group(1)), int(result.group(2)), int(result.group(3)))
    stack_to_pop_from = stacks[direction.original_stack - 1]
    stack_to_add_to = stacks[direction.destination_stack - 1]
    to_add = []
    for x in range(direction.how_many):
        to_add.append(stack_to_pop_from.pop())
    to_add.reverse()
    for x in to_add:
        stack_to_add_to.append(x)

l = []
for stack in stacks:
    l.append(stack.pop())

print("".join(l))


