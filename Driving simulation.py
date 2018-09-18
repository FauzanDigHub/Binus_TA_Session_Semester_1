print("Hello World")
IV = 0
time = int(input("input time spent:"))
accel = int(input("input accel:"))
distance = int(input("input dist:"))
limit = 60
V = IV + accel * time
dist = IV * time + 1/2 * accel * time * time

for i in range (0, time + 1):
    s = 1/2 * (accel * i * i)
    light = int(s//10)
    print("duration:", i, "distance:", "*" * light)

if V  >= 60:
    print("Max speed was", V, "m/s")
else:
    if V < 60:
        print("Max speed was", V, "m/s")

if s == 500:
    print("Reached", s, "m")
elif s>= 0 and s < 500:
    print("Reached", s, "m")



