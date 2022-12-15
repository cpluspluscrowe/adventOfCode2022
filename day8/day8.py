import numpy as np

trees = open("./inputTest.txt","r").read().split("\n")
print(trees)

def get_tree(i, j):
    if i < 0 or j < 0:
        return -1
    if i >= len(trees) or j >= len(trees[0]):
        return -1
    tree = trees[i][j]
    return tree

def get_adjacent_tree(i,j,x,y):
    return get_tree(i+x,j+y)

def rebuild_diagram(np_array):
    values = list(map(lambda x: str(x), [y for x in np_array for y in x]))
    as_str = "".join(values[0:3]) + "\n" + "".join(values[3:6]) + "\n" + "".join(values[6:])
    return as_str.replace("-1"," ")


def get_visualization(i,j):
    checks = get_checks(i,j)
    np_array = fill_out(checks,i,j)
    chart = rebuild_diagram(np_array)
    return (chart, np_array)


def fill_out(checks,i,j):
    empty = np.full([3,3],-1)
    empty[1][1] = get_tree(i,j)
    for row, col in checks:
        empty[row-i+1][col-j+1] = get_tree(row,col)
    return empty


def is_visible(np_array):
    tree = np_array[1][1]
    np_array[1][1] = -1
    highest = np_array.max()
    np_array[1][1] = tree
    return highest < tree


def get_checks(i,j):
    return [
        (i-1,j),
        (i,j+1),
        (i+1,j),
        (i,j - 1)
        ]

def get_grid(i,j, checks):
    grid = trees[i]

def is_outer_tree(i,j):
    if i == len(trees) - 1 or j == len(trees[0]) - 1:
        return True
    if i == 0 or j == 0:
        return True
    return False

count = 0
for i, _ in enumerate(trees):
    for j,_ in enumerate(trees[i]):
        chart, np_array = get_visualization(i,j)
        visibility = is_visible(np_array)
        print(chart)
        print(visibility, is_outer_tree(i,j))
        print()
        if visibility and not is_outer_tree(i,j):
            count += 1

print(count)
        
        
        



            

            
    

