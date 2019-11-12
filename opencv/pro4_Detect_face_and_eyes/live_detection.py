import cv2
import numpy as np

face_classifier = cv2.CascadeClassifier('../Haarcascades/haarcascade_frontalface_default.xml')
eye_classifier = cv2.CascadeClassifier('../Haarcascades/haarcascade_eye.xml')

def face_detector(img, size=0.5):
    # Convertir imagen a escala de grises
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    faces = face_classifier.detectMultiScale(gray, 1.3, 5)
    if(faces is ()):
        return img

    for (x,y,w,h) in faces:
        x -= 50
        w += 50
        y -= 50
        h += 50
        cv2.rectangle(img, (x,y), (x+w, y+h), (255,0,0),2)
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = img[y:y+h, x:x+w]
        eyes = eye_classifier.detectMultiScale(roi_gray)

        for (ex, ey, ew, eh) in eyes:
            cv2.rectangle(roi_color, (ex, ey), (ex+ew, ey+eh), (0,0,255),2)

    roi_color = cv2.flip(roi_color, 1)
    return roi_color

cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()
    if(ret == True):
        cv2.imshow("Our face extractor", face_detector(frame))
    if(cv2.waitKey(1) == 13): # 13 es la tecla enter
        break

cap.release()
cv2.destroyAllWindows()
