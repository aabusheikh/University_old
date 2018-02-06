'''
The attached file car_imperial.txt gives a list of cars and their fuel economy and trunk volume in
imperial units of miles per gallon and cubic feet, respectively. Write a Python script to generate a new
file car_metric.txt containing the same cars but with the fuel consumption in litres per 100 km and the
trunk volume in cubic meters.
'''

carsImperial = open("car_imperial.txt", "r")
carsMetric = open("car_metric.txt", "w")

while True:
    imperial = carsImperial.readline().strip()

    if imperial == "":
        break
    else:
        constant = imperial[:-16]

        fuelImperial = float(imperial[-16:-13])
        fuelMetric = 235.214/fuelImperial

        volImperial = float(imperial[-7:-5])
        volMetric = volImperial/35.314667

        metric = constant+" {0:.3g} litre/100 km, {1:.3g} m^3".format(fuelMetric, volMetric)

        carsMetric.write(metric+"\n")

carsImperial.close()
carsMetric.close()