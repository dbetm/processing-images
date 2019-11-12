import numpy as np
import cv2

# Cargar el clasificador en cascada
face_classifier = cv2.CascadeClassifier("../Haarcascades/haarcascade_frontalface_default.xml")

# Cargamos la imagen y la convertimos
# a escala de grises
image = cv2.imread("elon_musk.jpg")
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

""" Nuestro clasificador retorna el ROI de la cara
detectada como una tupla, guarda las coordenadas
(esquina superior izquierda, y la esquina inferior
derecha)
"""
faces = face_classifier.detectMultiScale(gray, 1.3, 5)

# Cuando no hay caras detectadas el clasificador
# detecta una tupla vacía
if faces is ():
    print("Caras no encontradas")

# Iteramos sobre los rostros y dibujamos un rectángulo
# sobre la imagen original del rosto detectado
for (x, y, w, h) in faces:
    cv2.rectangle(image, (x,y), (x+w,y+h), (127,0,255), 2)
    cv2.imshow("Face detection", image)
    cv2.waitKey(0)

cv2.destroyAllWindows()
