import numpy as np
from copy import deepcopy

lines = ""
with open("input.txt","r") as f:
    lines = f.read().split("\n")
    lines.remove("")

def increment_location(direction, number, location):
    y, x = location
    if direction == "R":
        x += number
    elif direction == "U":
        y -= number
    elif direction == "D":
        y += number
    elif direction == "L":
        x -= number
    return (y,x)

assert increment_location("R", 4, (0,0)) == (0, 4)
assert increment_location("U", 4, (0,0)) == (-4, 0)
assert increment_location("L", 4, (0,0)) == (0, -4)
assert increment_location("D", 4, (0,0)) == (4, 0)

def get_offset(location, tail_location):
    y = location[0] - tail_location[0]
    x = location[1] - tail_location[1]
    return (y, x)


def get_tail_movement(tail_location, offset):
    y, x = tail_location
    y_off, x_off = offset
    if(abs(y_off) <= 1 and abs(x_off) <= 1):
        return tail_location
    x_direction = 0 if x_off == 0 else x_off/abs(x_off)
    y_direction = 0 if y_off == 0 else y_off/abs(y_off)
    if y_off == 0:
        x += x_direction
        return (y, x)
    elif x_off == 0:
        y += y_direction
        return (y, x)
    else:
        y += y_direction
        x += x_direction
        return (y, x)


def visualize(locations):
    ys = list(map(lambda x: x[0], locations))
    xs = list(map(lambda x: x[1], locations))
    negative_y = min(ys)
    negative_x = min(xs)
    shift_ys = list(map(lambda y: int(y + abs(negative_y) + 1), ys))
    shift_xs = list(map(lambda x: int(x + abs(negative_x) + 1), xs))
    y_dim = int(max(1, max(shift_ys) + 1))
    x_dim = int(max(1, max(shift_xs) + 1))
    array = np.full([y_dim, x_dim], 0)
    knots = [-1] + [x for x in range(1,10)]
    index = len(knots) - 1
    to_traverse = list(zip(shift_ys, shift_xs))
    to_traverse.reverse()
    for y,x in to_traverse:
        array[y][x] = knots[index]
        index -= 1
    print(array)
    print()
    

location = (0,0)
tail_location = (0, 0)
history = set()

locations = []
for x in range(10):
    locations.append(location)


for line in lines:
    spl = line.split(" ")
    direction = spl[0]
    number = int(spl[1])
    for _ in range(number):
        locations[0] = increment_location(direction, 1, locations[0])
        for x in range(1, 10):
            location = locations[x-1]
            tail_location = locations[x]
            offset = get_offset(location, tail_location)
            locations[x] = get_tail_movement(tail_location, offset)
        history.add(deepcopy(locations[-1]))
        visualize(locations)
        
        
print(len(history))    
    
