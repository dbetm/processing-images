import cv2
import numpy as np

# Cargar imagen
image = cv2.imread("blobs.jpg", 0)
cv2.imshow("Imagen original", image)
cv2.waitKey(0)

# Iniciar el detector con los parámetros sin defecto
detector = cv2.SimpleBlobDetector_create()

# Detectar blobs
keypoints = detector.detect(image)

# Dibujar blobs en nuestra imagen como círculos rojos
blank = np.zeros((1,1))
blobs = cv2.drawKeypoints(image, keypoints, blank, (0,0,255), 0)

number_of_blobs = len(keypoints)
text = "Total number of blobs: " + str(number_of_blobs)
cv2.putText(blobs, text, (20, 45), cv2.FONT_HERSHEY_SIMPLEX, 1, (100, 0, 255), 1)

# Mostrar la imagen con puntos clave de los blobs
cv2.imshow("Blobs using default params: ", blobs)
cv2.waitKey(0)


cv2.destroyAllWindows()
