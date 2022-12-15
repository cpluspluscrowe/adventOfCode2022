# for any index, look above, below, left, and right. # we can provide splices# or we can just iterate via that splice.import numpy as nptrees = open("./input.txt","r").read().split("\n")array = np.full([len(trees), len(trees[0])], -1)for i, _ in enumerate(trees):    for j,tree in enumerate(trees[i]):        array[i][j] = treedef get_view_distance(array, tree):    if len(array) == 0:        return 0    count = 0    last = -1    for item in array:        if item < tree:            count += 1        else:            return count + 1    return count    #interior = 0#outer = 0scores = []for i, _ in enumerate(trees):    for j,tree in enumerate(trees[i]):        tree = int(tree)        right = array[i][j+1:]        left = array[i][:j]        above = array.transpose()[j][:i]        below = array.transpose()[j][i+1:]        #maxes= [right.max(initial = -1), left.max(initial = -1),above.max(initial = -1),below.max(initial = -1)]        #lowest_max = min(maxes)                above_view = get_view_distance(np.flip(above), tree)        left_view = get_view_distance(np.flip(left), tree)        right_view = get_view_distance(right, tree)        below_view = get_view_distance(below, tree)        scenic_points = above_view * left_view * right_view * below_view        scores.append(scenic_points)print(max(scores))scores_str = list(map(lambda x: str(x), scores))def make_chart(scores):    as_str = list(map(lambda x: str(x), scores))    s = ""    last = 0    for x in range(0, len(scores)+1,len(trees)):        s += ",".join(as_str[last:x]) + "\n"        last = x        return s    print(make_chart(scores))    print(max(scores))#        if lowest_max < int(tree):#            if(j > 0 and i > 0 ) and (i < len(array[0]) - 1 and j < len(array) - 1):#                interior += 1#            else:#                outer += 1#print(interior, outer)        # Part 2: best scenic score possible: 590824 (achieved by node (85, 46))