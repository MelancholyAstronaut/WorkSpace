class Solution
    def distanceBetweenBusStops(self, distance List[int], start int, destination int) - int
        if len(distance)==1
            return distance[0]
        if start  destination
        	start,destination = destination,start
        a = sum(distance[startdestination])
        del distance[startdestination]
        b = sum(distance)
        return min(a,b)