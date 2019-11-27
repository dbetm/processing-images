import cv2
import time
import numpy as np

# Crear nuestro clasificador de carros
car_classifier = cv2.CascadeClassifier('../Haarcascades/haarcascade_car.xml')

# Iniciar la captura del video desde el archivo
cap = cv2.VideoCapture('cars.avi')

# Iterar una vez que se ha cargado
while cap.isOpened():
    # Leer el primer frame
    time.sleep(0.004)
    ret, frame = cap.read()
    frame = cv2.resize(frame, None, fx=0.5, fy=0.5, interpolation=cv2.INTER_LINEAR)

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    # Pasar el marco al clasificador
    cars = car_classifier.detectMultiScale(gray, 1.2, 3)

    # Extraer marco para los cuerpo detectados y dibujarlos
    for (x,y,w,h) in cars:
        cv2.rectangle(frame, (x,y), (x+w, y+h), (0,255,255), 2)
        cv2.imshow("Carros", frame)

    if cv2.waitKey(1) == 13: # 13 es la tecla enter
        break;

cap.release()
cv2.destroyAllWindows()
