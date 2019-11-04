import cv2
import numpy as np

# Función que genera el sketch
def sketch(image):
    # Convertir imagen a escala de grises
    img_gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    # Limpiar la imagen usando desenfoque Gaussiano
    img_gray_blur = cv2.GaussianBlur(img_gray, (5,5), 0)

    # Detectar los bordes con el algoritmo de Canny
    canny_edges = cv2.Canny(img_gray_blur, 15, 90)

    # Hacer binarización inversa
    ret, mask = cv2.threshold(canny_edges, 70, 255, cv2.THRESH_BINARY_INV)
    return mask

# Inicializar webcam, cap es el objeto arrojado por la videocaptura
# Contiene una bandera indicando si se ha obtenido correctamente (ret)
# También contiene la imagen capturada de la cámara (frame)
cap = cv2.VideoCapture(0)

while(True):
    ret, frame = cap.read()
    if(ret == True):
        cv2.imshow('Our live sketcher', sketch(frame))
        if(cv2.waitKey(1) == 13): # 13 es la tecla enter
            break;

cap.release() # liberar la cámara
cv2.destroyAllWindows()
