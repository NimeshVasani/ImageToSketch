import cv2
import numpy as np
from android.content.res import Resources
from android.graphics import BitmapFactory

def process_image(resource_id):
    # Load the sample image from the resources using the resource ID
    resources = Resources.getSystem()
    sample_image = resources.openRawResource(resource_id)
    image_bytes = bytearray(sample_image.read())

    # Convert to a NumPy array
    image_data = np.frombuffer(image_bytes, dtype=np.uint8)
    image = cv2.imdecode(image_data, cv2.IMREAD_COLOR)

    # Convert the image to grayscale
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    # Convert the grayscale image back to bytes
    _, processed_image_bytes = cv2.imencode(".png", gray_image)

    # Convert the processed image byte array to a string
    processed_image_str = processed_image_bytes.tobytes().decode("ISO-8859-1")

    # Return the processed image as a string
    return processed_image_str
