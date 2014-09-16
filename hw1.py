#!/usr/bin/env python2

from ast import literal_eval as make_tuple
import sys
        

class Polygon:
    def __init__(self, list_of_points):
        self.points = list_of_points
        
    def __str__(self):
        return str(self.points)
    
    def get_edge(self, index):
        return (self.points[index], self.points[(index + 1) % len(self.points)])

    def where_is_point(self, point):
        c = True
        for i in range(len(self.points)):
            edge = self.get_edge(i)
            if cross_or_bound(point, edge) == 2:
                return 1
            elif cross_or_bound(point, edge) == 0:
                c = not c
        if c:
            return 2
        else:
            return 0


def cross_or_bound(point, edge):
    if left_or_right(point, edge) == 3:
        if ((edge[0][1] < point[1]) and (point[1] <= edge[1][1])):
            return 0
        else:
            return 1
    elif left_or_right(point, edge) == 4:
        if ((edge[1][1] < point[1]) and (point[1] <= edge[0][1])):
            return 0
        else:
            return 1
    elif left_or_right(point, edge) == 5:
        return 1
    else:
        return 2



def left_or_right(point, edge):
    if (edge[0] == point):
        return 0
    if (edge[1] == point):
        return 1

    position = ((edge[1][0] - edge[0][0]) * (point[1] - edge[0][1]) -
                (edge[1][1] - edge[0][1]) * (point[0] - edge[0][0]))
    if (position > 0):
        return 3
    elif position < 0:
        return 4
    if (((edge[1][0] - edge[0][0]) * (point[0] - edge[0][0]) < 0) or
        ((edge[1][1] - edge[0][1]) * (point[1] - edge[0][1]) < 0) or
        ((edge[1][0] - edge[0][0])**2 +
         (edge[1][1] - edge[0][1])**2 <
         (point[0] - edge[0][0]) ** 2 +
         (point[1] - edge[0][1]) ** 2)):
        return 5
    elif position == 0:
        return 2

   
def parse_and_initialize(filename):
    points_to_check = []
    polygon = 0
    with open(filename) as f:
        future_length = 0
        counter = 0
        for line in f:
            splitted_line = line.split(",")
            if len(splitted_line) == 1:
                future_length = int(splitted_line[0])
                counter = 0
                polygon = Polygon(points_to_check)
                points_to_check = []
            else:
                if counter < future_length:
                    points_to_check.append(make_tuple(line))
                else:
                    print "Smth wrong with the number of points. Abort."
                    sys.exit()
                counter += 1
    return points_to_check, polygon


def understand_in_or_not(points_to_check, polygon):
    for point in points_to_check:
        if polygon.where_is_point(point) != 2:
            print "YES"
        else:
            print "NO"
                

def main():
    args = sys.argv
    points_to_check, polygon = parse_and_initialize(args[1])
    understand_in_or_not(points_to_check, polygon)
    
main()
        
        
