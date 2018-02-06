'''
1. Create a class House with the following instance variables :
    • A list of strings for the rooms in the house
    • A string with the name 'House'
House should have the following methods:
    • A constructor with an optional argument of a list of strings. Without the optional argument
      the object should have a list of rooms [ 'kitchen', 'living', 'dinning', 'main' ] while with an
      optional argument ['bedroom 1', 'bedroom 2'], the list of rooms should be [ 'kitchen', 'living',
      'dinning', 'main', 'bedroom 1', 'bedroom 2'].
    • A method inputSqft that allows the user to input the size of each room
    • A method printMetric that prints all the rooms in the house but in meters instead of feet
'''
class House:
    def __init__(self, l=None):
        self.sizes = []
        self.name = "House"
        if l is None:
            self.rooms = ['kitchen', 'living', 'dinning', 'main']
        else:
            self.rooms = ['kitchen', 'living', 'dinning', 'main']+l

    def inputSqft(self):
        self.sizes = []
        for room in self.rooms:
            size = input(room+": width x length:")
            n = size.index("x")
            w = "{0:.3g}".format(float(size[:n]))
            l = "{0:.3g}".format(float(size[n+1:]))
            self.sizes.append(w+"x"+l)

    def printMetric(self):
        print(self.name)
        for i in range(len(self.rooms)):
            size = self.sizes[i]
            n = size.index("x")
            w = "{0:.3g}".format(float(size[:n])/3.28084)
            l = "{0:.3g}".format(float(size[n + 1:])/3.28084)
            print(self.rooms[i]+": "+w+" x "+l+" m")

#h1 = House()
#print(h1.rooms)
#h2 = House(['bedroom 1', 'bedroom 2'])
#print(h2.rooms)
#h2.inputSqft()
#h2.printMetric()


'''
2. Derive a class Semi from House. The only new method is a constructor that works identical to
House and simply sets the room instance variable in House by calling the constructor in House.
Semi should have its name set to 'Semi'.
'''
class Semi(House):
    def __init__(self, l=None):
        self.name = "Semi"
        h = House(l)
        self.rooms = h.rooms
        self.sizes = h.sizes

#h3 = Semi()
#h3.inputSqft()
#h3.printMetric()
