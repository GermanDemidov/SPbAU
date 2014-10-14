#!/usr/bin/env python2

from ast import literal_eval as make_tuple
import sys
import copy


def orientation(a, b, c):
    det = (b[0] - a[0]) * (c[1] - a[1]) - (c[0] - a[0]) * (b[1] - a[1])
    if det < 0:
        return "Left"
    elif det > 0:
        return "Right"
    elif det == 0:
        return "Collinear"

def position(a, b, c):
    value = orientation(a, b, c)
    if value == "Left":
        return "Left"
    elif value == "Right":
        return "Right"
    else:
        fst = b[0] - a[0]
        snd = b[1] - a[1]
        trd = c[0] - a[0]
        fth = c[1] - a[1]
        if (fst * trd < 0) or (snd * fth < 0):
            return "Behind"
        if (fst ** 2 + snd ** 2) < (trd ** 2 + fth ** 2):
            return "Beyond"
        else:
            return "Between"

    
def claffify_left(a, b, c, is_left):
    class_of_point = position(a, b, c)
    if class_of_point == "Left":
        return True
    elif class_of_point == "Between":
        return is_left
    elif class_of_point == "Beyond":
        return (not is_left)
    else:
        return False


def claffify_right(a, b, c, is_left):
    class_of_point = position(a, b, c)
    if class_of_point == "Right":
        return True
    elif class_of_point == "Between":
        return (not is_left)
    elif class_of_point == "Beyond":
        return is_left
    else:
        return False


def find_tangent_curve(polygon, point, is_left):
    left = claffify_left
    right = claffify_right
    if is_left:
        left, right = right, left
    vertices = copy.deepcopy(polygon)
    vertices.append(polygon[0])
    n = len(polygon)
    # binary search
    med = 0
    l_bound = 0
    r_bound = n
    left_index = 1
    right_index = n - 1
    if is_left:
        left_index, right_index = right_index, left_index
    if (right(point, vertices[left_index], vertices[0], is_left) and
        (not left(point, vertices[right_index], vertices[0], is_left))):
        return 0

    while (l_bound + 1 != r_bound):
        med = (l_bound + r_bound) / 2
        d = is_left
        if is_left:
            d = not (left(point, vertices[med + 1], vertices[med], is_left))
        else:
            d = right(point, vertices[med + 1], vertices[med], is_left)

        k = is_left
        if is_left:
            k = right(point, vertices[med - 1], vertices[med], is_left)
        else:
            k = not left(point, vertices[med - 1], vertices[med], is_left)

        if d and k:
            return med

        if left(point, vertices[l_bound + 1], vertices[l_bound], is_left):
            if d or left(point, vertices[l_bound], vertices[med], is_left):
                r_bound = med
            else:
                l_bound = med
        else:
            if not d or not right(point, vertices[l_bound], vertices[med], is_left):
                l_bound = med
            else:
                r_bound = med
    return l_bound

                
def solver(polygon, point):
    ans = ""
    for is_left in [False, True]:
        elem = str(find_tangent_curve(polygon, point, is_left))
        ans += elem + " "
    print ans

   
def parse_and_initialize(filename):
    points_to_check = []
    polygon = []
    with open(filename) as f:
        for line in f:
            string = []
            tmp_coord = ""
            for elem in line:
                if elem.isdigit() or elem == "-":
                    tmp_coord += elem
                if elem in [",",")","\n"] and tmp_coord:
                    string.append(int(tmp_coord))
                    tmp_coord = ""
            if len(string) == 0:
                continue
            elif len(string) == 1:
                polygon = points_to_check
                points_to_check = []
                future_length = string[0]
            elif len(string) == 2:
                points_to_check.append(string)
                future_length -= 1
                if future_length < 0:
                    print "Smth wrong with num of points"
                    sys.exit()
    return points_to_check, polygon

                
def main():
    args = sys.argv
    points_to_check, polygon = parse_and_initialize(args[1])
    for point in points_to_check:
        solver(polygon, point)
    
    
main()
        
        
