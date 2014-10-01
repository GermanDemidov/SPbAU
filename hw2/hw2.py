#!/usr/bin/env python2

from ast import literal_eval as make_tuple
import sys


def merge(l, r):
    result = []
    indexes = []
    i = j = 0
    while i < len(l) and j < len(r):
        if l[i] < r[j]:
            result.append(l[i])
            indexes.append(i)
            i += 1
        else:
            result.append(r[j])
            indexes.append(len(r) - 1 - j + len(l))
            j += 1
    result += l[i:]
    for k in range(i, len(l)):
        indexes.append(k)
    result += r[j:]
    for k in range(j, len(r)):
        indexes.append(len(r) - 1 - j + len(l))
    return result, indexes
        

class Polygon:
    def __init__(self, list_of_points):
        self.points = tuple(list_of_points)
        self.maxindex = 0
        max_x = self.points[0][0]
        for i in range(len(self.points)):
            if self.points[i] > max_x:
                max_x = self.points[i][0]
                self.maxindex = i
            else:
                break

        list1 = list(self.points[:self.maxindex - 1])
        list2 = list(reversed(self.points[self.maxindex - 1:]))

        """self.bottom_points = {}
        self.top_points = {}
        for i in range(maxindex - 1):
            self.bottom_points[self.points[i]] = i
        for i in range(len(self.points) - 1, maxindex - 2, -1):
            self.top_points[self.points[i]] = i"""

        self.event, indexes = merge(list1, list2)
        self.events = indexes

    def from_top_chain(self, point):
        if point >= self.maxindex:
            return True
        return False
        
    def __str__(self):
        return str(self.points)
    
    def get_edge(self, index):
        return (self.points[index],
                self.points[(index + 1) % len(self.points)])


class Triangle:
    def __init__(self, list_of_points):
        if len(list_of_points) == 3:
            self.points = (sorted(list_of_points))
        else:
            print "Not a Triangle", list_of_points

    def __str__(self):
        return str(tuple(self.points))

def orientation(a, b, c):
    det = (b[0] - a[0]) * (c[1] - a[1]) - (c[0] - a[0]) * (b[1] - a[1])
    if det < 0:
        return -1
    elif det > 0:
        return 1
    elif det == 0:
        return 0
    else:
        return 0
    

def triangulate(polygon):
    ans = []
    stack = []
    stack.extend(polygon.events[0:2])
    for i in range(2, len(polygon.points)):
        tmp = polygon.events[i]
        stack_top = stack[len(stack) - 1]
        if not ((polygon.from_top_chain(tmp)) == (polygon.from_top_chain(stack_top))):
            while len(stack) > 1:
                fst = stack.pop()
                snd = stack[len(stack) - 1]
                ans.append(Triangle([tmp, fst, snd]))
                # print "ANS1", [tmp, fst, snd]
            stack.pop()
            stack.append(stack_top)
            stack.append(tmp)
        else:
            while len(stack) > 1:
                fst = stack[len(stack) - 1]
                snd = stack[len(stack) - 2]
                orient = orientation(polygon.points[tmp], polygon.points[snd], polygon.points[fst])
                if (orient == 0) or ((orient == -1) != (polygon.from_top_chain(fst))):
                    break
                else:
                    stack.pop()
                    ans.append(Triangle([tmp, fst, snd]))
                    # print "ANS2", [tmp, fst, snd]
            stack.append(tmp)
    for elem in ans:
        print elem
    return ans
   
def parse_and_initialize(filename):
    points_to_check = []
    polygon = 0
    with open(filename) as f:
        for line in f:
            splitted_line = line.split(",")
            if len(splitted_line) == 1:
                future_length = int(splitted_line[0])
                counter = 0
            else:
                if counter < future_length:
                    points_to_check.append(make_tuple(line))
                else:
                    print "Smth wrong with the number of points. Abort."
                    sys.exit()
                    
    polygon = Polygon(points_to_check)
    return polygon
                

def main():
    args = sys.argv
    polygon = parse_and_initialize(args[1])
    triangulate(polygon)
    
main()
        
        
