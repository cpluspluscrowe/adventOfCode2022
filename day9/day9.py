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


location = (0,0)
tail_location = (0, 0)
history = set()

    
for line in lines:
    spl = line.split(" ")
    direction = spl[0]
    number = int(spl[1])
    for _ in range(number):
        location = increment_location(direction, 1, location)
        offset = get_offset(location, tail_location)
        tail_location = get_tail_movement(tail_location, offset)
        print(location, tail_location)
        history.add(deepcopy(tail_location))
        

print(len(history))    
    
        
    
# now the trick is updating the tail's location.
