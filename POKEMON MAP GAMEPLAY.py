data = []

def read():
    file = open('POKEMON MAP.txt', 'r')
    for row in file:
        content = row.split(" ")
        temp =[]
        for x in range (0, len(content)):
            temp.append(content[x])
        data.append(temp)
    file.close()
print(data)
def write():
    temp=""
    file= open("POKEMON MAP.txt", "w")
    for x in range (0,8):
        for y in range (0,8):
            temp += str(data[x][y] + "")
    file.write(temp)
    file.close()
def view():
    for x in range (0,8):
        for y in range(0,8):
            if data[x][y] == 2:
                print("#",end=" ")
            elif data[x][y] == 0:
                print("0", end=" ")
#def up():


#def down():

#def left():

#def right():
while 1:
        view()
        print("CONTROLLER")
        print("1. up")
        print("2. right")
        print("3. left")
        print("4. down")
        print("5. exit")
        c = input("input option:")
        if c == 1:
            print("up")
        elif c == 2:
            print("right")
        elif c == 3:
            print("left")
        elif c == 4:
            print("down")
        elif c == 5:
            print("exit")
            break
        else:
            print("wrong input")



