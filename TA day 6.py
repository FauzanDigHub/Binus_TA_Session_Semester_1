def factorial(number):
    if number == 0:
        return (1)
    else:
        return (number * factorial(number-1))
number=int(input("Input number to result in factiorial : "))
print("")
print("result:",factorial(number))



