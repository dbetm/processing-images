import cv2
import numpy as np

# Crear nuestro clasificador de cuerpo
body_classifier = cv2.CascadeClassifier('../Haarcascades/haarcascade_fullbody.xml')

# Iniciar la captura del video desde el archivo
cap = cv2.VideoCapture('walking.avi')

# Iterar una vez que se ha cargado
while cap.isOpened():
    # Leer el primer frame
    ret, frame = cap.read()
    frame = cv2.resize(frame, None, fx=0.5, fy=0.5, interpolation=cv2.INTER_LINEAR)

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    # Pasar el marco al clasificador
    bodies = body_classifier.detectMultiScale(gray, 1.2, 3)

    # Extraer marco para los cuerpo detectados y dibujarlos
    for (x,y,w,h) in bodies:
        cv2.rectangle(frame, (x,y), (x+w, y+h), (0,255,255), 2)
        cv2.imshow("Peatones", frame)

    if cv2.waitKey(1) == 13: # 13 es la tecla enter
        break;

cap.release()
cv2.destroyAllWindows()

