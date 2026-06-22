
CREATE DATABASE ProjectWeb;
USE ProjectWeb;
-- Tạo bảng Tour
CREATE TABLE Tour (
    tourId INT PRIMARY KEY,
    tenTour NVARCHAR(255),
    duration NVARCHAR(255),
    schedule NVARCHAR(255),
    departure NVARCHAR(255),
    price FLOAT,
    transport NVARCHAR(255)
);

-- Tạo bảng imgTour (nếu cần)
CREATE TABLE imgTour (
    imgId INT PRIMARY KEY,
    tourId INT,
    img1 NVARCHAR(255),
    img2 NVARCHAR(255),
    img3 NVARCHAR(255),
    img4 NVARCHAR(255),
    img5 NVARCHAR(255),
    img6 NVARCHAR(255),
    FOREIGN KEY (tourId) REFERENCES Tour(tourId)
);
CREATE TABLE Users (
    username NVARCHAR(255) PRIMARY KEY,
    password NVARCHAR(255),
);
INSERT INTO Users (username, password) VALUES ('Admin', '123@');
-- Tạo bảng Customer
CREATE TABLE Customer (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255),
    address NVARCHAR(255),
    email NVARCHAR(255),
    phone NVARCHAR(20),
    username NVARCHAR(255),
    role NVARCHAR(20)  DEFAULT 'Customer' ,
    CONSTRAINT FK_Customer_Users FOREIGN KEY (username) REFERENCES Users(username)
);
INSERT INTO Customer (name, address, email, phone, username, role)
VALUES 
('Nguyen Phi Long', '1234 QL1K, HCM', 'nplong10c2@gmail.com', '0947659052', 'Admin', 'Admin');
select * from Customer
CREATE TABLE Booking (
    id INT PRIMARY KEY IDENTITY(1,1),
    departureDate DATE,
    noAdults INT,
    noChildren INT,
    email NVARCHAR(255),
    tourId INT,
    customerId INT,
    FOREIGN KEY (tourId) REFERENCES Tour(tourId),
    FOREIGN KEY (customerId) REFERENCES Customer(id)
);

-- Thêm dữ liệu vào bảng Tour
INSERT INTO Tour (tourId, tenTour, duration, schedule, departure, price, transport) 
VALUES 
(1, 'Tour Da Lat', '5 ngay', 'Thu 2 toi Thu 6', '2026-06-01', 5000000, 'Bus'),
(2, 'Tour Đa Nang', '3 ngay', 'Thu 4 toi Thu 7', '2026-07-15', 6750000, 'Train'),
(3, 'Tour Hoi An', '7 ngay', 'Thu 2 toi Thu 6', '2026-08-10', 6000000, 'Car'),
(4, 'Tour Ha Giang', '7 ngay', 'Thu 2 toi Thu 7', '2026-08-10', 8000000, 'Car'),
(5, 'Tour Hue', '5 ngay', 'Thu 3 toi Thu 6', '2026-08-10', 6500000, 'Plane'),
(6, 'Tour Ninh Binh', '3 ngay', 'Thu 4 toi Thu 6', '2026-08-10', 7000000, 'Plane'),
(7, 'Tour Nha Trang', '4 ngay', 'Thu 4 toi Thu 2', '2026-08-10', 4000000, 'Car'),
(8, 'Tour Phu Quoc', '5 ngay', 'Thu 4 toi Thu 6', '2026-08-10', 10000000, 'Plane'),
(9, 'Tour SaPa', '2 ngay', 'Thu 2 toi Thu 4', '2026-08-10', 8000000, 'Car');

-- Thêm dữ liệu vào bảng imgTour
INSERT INTO imgTour (imgId, tourId, img1, img2, img3, img4, img5, img6)
VALUES
(1, 1, 'TourDL3.jpg', 'TourDL3.jpg', 'TourDL3.jpg', 'TourDL4.jpg', 'TourDL5.jpg', 'TourDL6.jpg'),
(2, 2, 'TourDN.jpg', 'TourDN.jpg', 'TourDN3.jpg', 'TourDN4.jpg', 'TourDN4.jpg', 'TourDN4.jpg'),
(3, 3, 'TourHA.jpg', 'TourHA.jpg', 'TourHA3.jpg', 'TourHA4.jpg', 'TourHA5.jpg', 'TourHA6.jpg'),
(4, 4, 'TourHG3.jpg', 'TourHG3.jpg', 'TourHG3.jpg', 'TourHG4.jpg', 'TourHG5.jpg', 'TourHG5.jpg'),
(5, 5, 'TourHue3.jpg', 'TourHue3.jpg', 'TourHue3.jpg', 'TourHue3.jpg', 'TourHue3.jpg', 'TourHue3.jpg'),
(6, 6, 'TourNB3.jpg', 'TourNB3.jpg', 'TourNB3.jpg', 'TourNB3.jpg', 'TourNB4.jpg', 'TourNB6.jpg'),
(7, 7, 'TourNT.jpg', 'TourNT.jpg', 'TourNT3.jpg', 'TourNT3.jpg', 'TourNT4.jpg', 'TourNT4.jpg'),
(8, 8, 'TourPQ4.jpg', 'TourPQ4.jpg', 'TourPQ4.jpg', 'TourPQ4.jpg', 'TourPQ4.jpg', 'TourPQ4.jpg'),
(9, 9, 'TourSP3.jpg', 'TourSP3.jpg', 'TourSP3.jpg', 'TourSP4.jpg', 'TourSP4.jpg', 'TourSP6.jpg');

--check demo
select * from Users
select * from Customer
