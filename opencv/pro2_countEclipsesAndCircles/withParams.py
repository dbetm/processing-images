import cv2
import numpy as np

# Cargar imagen
image = cv2.imread("blobs.jpg", 0)
cv2.imshow("Imagen original", image)
cv2.waitKey(0)

# Setear los parámteros del filtro
params = cv2.SimpleBlobDetector_Params()

# Fijar el filtro para el área
params.filterByArea = True
params.minArea = 100

# Fijar el filtro para la circularidad
params.filterByCircularity = True
params.minCircularity = 0.9

# Fijar el filtro para la convexidad
params.filterByConvexity = False
params.minConvexity = 0.2

# Fijar el filtro para la inercia
params.filterByInertia = True
params.minInertiaRatio = 0.01

# Crear un detector con los parámetros
detector = cv2.SimpleBlobDetector_create(params)

# Detectar Blobs
keypoints = detector.detect(image)

# Dibujar blobs es nuestra imagen como círculos rojos
blank = np.zeros((1,1))
blobs = cv2.drawKeypoints(image, keypoints, blank, (0, 255, 0), 4)

number_of_blobs = len(keypoints)
text = "Total number of blobs: " + str(number_of_blobs)
cv2.putText(blobs, text, (20, 45), cv2.FONT_HERSHEY_SIMPLEX, 1, (100, 0, 255), 1)

# Mostrar la imagen con puntos clave de los blobs
cv2.imshow("Blobs using default params: ", blobs)
cv2.waitKey(0)

cv2.destroyAllWindows()
