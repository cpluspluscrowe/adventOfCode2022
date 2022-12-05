input1 = '''vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw'''
input2 = '''vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw'''

text = open("/Users/ccrowe/github/adventOfCode2022/day3/input.txt", "r").read().split("\n") # input2.split("\n")#

groups = []
group = []
for i, line in enumerate(text):
    group.append(line)
    if (i+1) % 3 == 0:
        groups.append(group)
        group = []

def common_value(group):
    s0 = set(group[0])
    s1 = set(group[1])
    s2 = set(group[2])
    return list(s0.intersection(s1).intersection(s2))[0]

def get_value(char: str) -> int:
    is_capitalized = char.upper() == char
    ascii_value = ord(char.lower()) - 96
    if(is_capitalized):
        return ascii_value + 26
    return ascii_value
    
def get_two_halves(line):
    half = int(len(line)/2)
    return (line[:half], line[half:])

def get_same_letter(halves):
    return list(set(halves[0]).intersection(set(halves[1])))[0]

summation = 0
for group in groups:
    letter = common_value(group)
    value = get_value(letter)
    summation += value
print(summation)

# day 1
#summation = 0    
#for line in lines:
#    halves = get_two_halves(line)
#    letter = get_same_letter(halves)
#    value = get_value(letter)
#    summation += value
#print(summation)
