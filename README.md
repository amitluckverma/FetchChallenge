Running the app:
![Runing App](https://github.com/user-attachments/assets/084f92c9-c6ed-4c4f-b959-25b9e5a58244)

Running and getting output:
![Running Command](https://github.com/user-attachments/assets/475e13bb-6bd6-4ea9-8300-c6a3cbf1a62b)

You can also use Eclipse to run the application:

Steps to run in Eclipse:

1)Run the main class ReceiptProcessorApplication.java.
2)Open Postman and follow these steps:
3)Set the request type to POST.
4)Enter the request URL: http://localhost:8080/receipts/process.
5)Go to the Body tab and select raw.
6)Select JSON from the dropdown next to the raw option.
7)Copy the content of morning-receipt.json (which should be in the src/main/resources folder) and paste it into the body section.
8)Click on send.
9)You will receive an ID in the response.
10)Then use GET with http://localhost:8080/receipts/{ID}/points.

