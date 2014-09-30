#!/usr/bin/env python2

from ast import literal_eval as make_tuple
import sys
        

class Polygon:
    def __init__(self, list_of_points):
        self.points = tuple(list_of_points)
        maxindex = 0
        max_x = self.points[0][0]
        for i in range(len(self.points)):
            if self.points[i] > max_x:
                max_x = self.points[i]
                maxindex = i
            else:
                break
        self.bottom_points = self.points[:maxindex]
        self.top_points = self.points[maxindex:]
        self.events = self.points[:maxindex]
        self.events = sorted(self.points)
        
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
        string = ""
        for point in self.points:
            string += str(point)
        return string

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
        if not (tmp in polygon.top_points and stack_top in polygon.top_points):
            while len(stack) > 1:
                fst = stack.pop()
                snd = stack[len(stack) - 1]
                ans.append(Triangle([tmp, fst, snd]))
            stack.pop()
            stack.append(stack_top)
            stack.append(tmp)
        else:
            while len(stack) > 1:
                fst = stack[len(stack) - 1]
                snd = stack[len(stack) - 2]
                orient = orientation(tmp, snd, fst)
                if (orient == 0) or not ((orient == -1) == (fst in polygon.top_points)):
                    break
                else:
                    stack.pop()
                    ans.append(Triangle([tmp, fst, snd]))
            stack.append(tmp)
    for elem in ans:
        lst = []
        for point in elem.points:
            lst.append(polygon.points.index(point))
        print tuple(sorted(lst))
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
        
        
