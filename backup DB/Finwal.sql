-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 07, 2017 at 03:31 PM
-- Server version: 5.7.20-0ubuntu0.16.04.1
-- PHP Version: 7.0.22-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Finwal`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `cust_id` char(255) NOT NULL,
  `email` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`cust_id`, `email`) VALUES
('Inoo8lGhGWfDDqA5Gn3wuKXjDj42', '12346@gmail.com'),
('02tfWQmgJVfQQiPgm2z9sPKvfK32', 'apoyppcy@gmail.com'),
('O27fRZ9UWCcIr0QvgQ1nMuDyLz92', 'bunthit@gmail.com'),
('0Y9ef0KCOWMKNgtgQz4HYA8QmD32', 'hello@hotmail.com'),
('ivrnJVuwewTAEhvWufhyyOXnOSl1', 'kkkk@vma.com'),
('H0kDJbx8UecmvXH6m5aqHHltFkY2', 'lanturn@gmail.com'),
('dFqwZ1x5slXHypVOSLgXSzSUJGp1', 'pimmy.abcd@hotmail.com'),
('YAzv82iDRbYVJerjYzO0ZsFtl8e2', 'pleum101@gmail.com'),
('XzBclRuGXFRnk5xGhwh0bzULWyx2', 'pleum@gmail.com'),
('NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'poy@gmail.com'),
('W5zo4Ie5QtTWwb97YirzWMZh9wT2', 'ppcy@hotmail.com'),
('NRE7Grn720QBVA08O5BiifAlW8D2', 'qq@gmail.com'),
('fXm9nPVYqWSPYaiod6rv23LXkbF3', 'rr@gmail.com'),
('sF8JP69wQpOQ2R0YzNB15WkgPdE2', 'ss@gmail.com'),
('W7sE4C7L6rOiXxTzb56WOSic4Mq1', 'sss@gmail.com'),
('9hG3lYvNswNqe19X3wrOB5GmRqj1', 'test1211@gmail.com'),
('gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'test@gmail.com'),
('2omBuAKwPdVBaELMXa9ZbguJKyC2', 'wanngai@hotmail.com'),
('1NzulQpFxYadvpJsgXn3dX2dxLw2', 'wc@hotmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `goal`
--

CREATE TABLE `goal` (
  `goal_id` int(10) NOT NULL,
  `cust_id` varchar(255) NOT NULL,
  `ending_date` varchar(255) NOT NULL,
  `description_goal` varchar(510) NOT NULL,
  `status_goal` varchar(255) NOT NULL DEFAULT 'On Process',
  `budget_goal` double NOT NULL,
  `savingplan` varchar(255) NOT NULL,
  `suggest_cost` double NOT NULL,
  `current_goal` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `goal`
--

INSERT INTO `goal` (`goal_id`, `cust_id`, `ending_date`, `description_goal`, `status_goal`, `budget_goal`, `savingplan`, `suggest_cost`, `current_goal`) VALUES
(21, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '23-09-2017', 'travel', 'Achieved', 352452, 'Weekly', 0, 0),
(25, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '23-09-2017', 'Daily', 'Achieved', 66757, 'Daily', 0, 0),
(26, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '23-09-2017', 'Weekly', 'Deleted', 345, 'Weekly', 0, 0),
(27, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '14-10-2017', 'Monthly', 'Deleted', 5646, 'Monthly', 0, 0),
(34, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '23-09-2017', 'Fan', 'Achieved', 32556, 'Daily', 0, 0),
(36, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '23-09-2017', 'go', 'Deleted', 4766, 'Daily', 0, 0),
(38, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '28-09-2017', 'travel', 'Achieved', 30000, 'Weekly', 0, 40),
(41, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '21-08-2017', 'hello', 'Deleted', 352, 'Weekly', 4524, 452),
(43, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '23-02-2017', 'world', 'Achieved', 23, 'Weekly', 23, 40),
(45, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '30-12-2017', 'tomorrowland', 'Achieved', 32345, 'Daily', 323.45, 50034),
(47, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '30-09-2017', 'testEditGoal', 'Deleted', 356, 'Daily', 44.5, 356),
(52, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '19-10-2017', 'Car', 'Achieved', 2000, 'Weekly', 2000, 2000),
(58, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '30-11-2017', 'Japan', 'Achieved', 3000, 'Weekly', 500, 3000),
(61, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '30-10-2017', 'test3', 'Unachieve', 2345, 'Daily', 335, 0),
(62, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '30-10-2017', 'test2', 'Unachieve', 34567, 'Daily', 4320.88, 0),
(63, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '31-10-2017', 'Japan', 'Achieved', 500, 'Daily', 62.5, 50),
(85, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '03-01-2018', 'Korea with friends', 'On Process', 20000, 'Weekly', 2222.22, 5500),
(90, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '05-12-2017', 'Japan', 'Deleted', 20000, 'Weekly', 4000, 0),
(91, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '17-01-2018', 'Car', 'On Process', 22222, 'Weekly', 222, 0),
(93, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '28-11-2017', 'Car', 'On Process', 2342, 'Daily', 101.83, 200),
(94, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '26-12-2017', 'Taiwan', 'Achieved', 20000, 'Daily', 384.62, 500384.62),
(95, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '31-12-2017', 'Japan', 'On Process', 20000, 'Weekly', 2857.14, 10000),
(96, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '10-04-2018', 'Europe', 'On Process', 20000, 'Weekly', 952.38, 3000),
(97, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '26-01-2018', 'Japan', 'On Process', 10000, 'Weekly', 1000, 1234),
(98, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '30-01-2018', 'toy', 'On Process', 1000, 'Daily', 13.16, 50),
(99, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '23-01-2018', 'Japannnnn', 'On Process', 2000000, 'Weekly', 222222.22, 0),
(100, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '23-01-2018', 'Japannnnn', 'On Process', 20000, 'Weekly', 2222.22, 0),
(101, 'YAzv82iDRbYVJerjYzO0ZsFtl8e2', '15-01-2018', 'africa', 'Unachieve', 15000, 'Monthly', 15000, 12000);

-- --------------------------------------------------------

--
-- Table structure for table `period`
--

CREATE TABLE `period` (
  `bill_id` int(10) NOT NULL,
  `cust_id` varchar(255) NOT NULL,
  `period` char(255) NOT NULL,
  `description_bill` varchar(510) NOT NULL,
  `status_bill` varchar(255) NOT NULL DEFAULT 'Unpaid',
  `deadline` char(10) NOT NULL,
  `paid_date` varchar(255) NOT NULL DEFAULT 'Unpaid'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `period`
--

INSERT INTO `period` (`bill_id`, `cust_id`, `period`, `description_bill`, `status_bill`, `deadline`, `paid_date`) VALUES
(3, '1NzulQpFxYadvpJsgXn3dX2dxLw2', 'Monthly', 'mobile', 'Paid', '11-09-2017', '2017-10-19'),
(4, '1NzulQpFxYadvpJsgXn3dX2dxLw2', 'Weekly', 'me', 'Paid', '13-08-2017', '2017-10-19'),
(5, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'water', 'Deleted', '07-08-2017', 'Unpaid'),
(6, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'water', 'Paid', '22-08-2017', '2017-10-19'),
(23, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'AIS', 'Paid', '12-11-2017', '2017-11-09'),
(24, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'TRUE', 'Paid', '10-10-2017', '2017-11-09'),
(25, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'Dtac', 'Paid', '20-08-2017', '2017-10-19'),
(26, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Weekly', 'water', 'Paid', '28-08-2018', '2017-10-31'),
(28, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Weekly', 'dorm', 'Deleted', '23-04-2017', 'Unpaid'),
(29, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Monthly', 'Dorm', 'Paid', '21-08-2017', '2017-10-30'),
(30, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Weekly', 'Japan', 'Paid', '21-71-2018', '2017-10-31'),
(31, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Weekly', 'Go', 'Deleted', '25-06-2018', 'Unpaid'),
(33, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Weekly', 'YOLO', 'Unpaid', '26-11-2023', 'Unpaid'),
(37, 'dFqwZ1x5slXHypVOSLgXSzSUJGp1', 'Monthly', 'fire', 'Paid', '30-09-2017', '2017-10-19'),
(38, 'dFqwZ1x5slXHypVOSLgXSzSUJGp1', 'Monthly', 'water', 'Unpaid', '09-11-2017', 'Unpaid'),
(40, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'fire', 'Deleted', '30-09-2017', 'Unpaid'),
(41, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'water', 'Paid', '19-10-2017', '2017-11-11'),
(42, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'test', 'Unpaid', '26-01-2019', '2017-10-30'),
(43, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'phone', 'Paid', '25-10-2017', '2017-10-19'),
(44, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'phone', 'Paid', '31-10-2017', '2017-10-19'),
(45, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'huh', 'Paid', '26-11-2018', '2017-11-06'),
(46, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Monthly', 'teset', 'Paid', '25-01-2019', '2017-10-31'),
(47, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '6 Monthly', '6m', 'Paid', '25-11-2020', '2017-10-31'),
(48, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Yearly', 'year', 'Paid', '26-10-2027', '2017-10-31'),
(49, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Weekly', 'testweek', 'Paid', '22-10-2017', '2017-11-09'),
(50, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '6 Monthly', 'test6month', 'Deleted', '10-04-2019', 'Unpaid'),
(51, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Yearly', 'testyear', 'Unpaid', '31-01-2018', '2017-11-11'),
(52, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Monthly', 'new11month', 'Paid', '28-02-2018', '2017-10-31'),
(53, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Monthly', '11month', 'Paid', '30-00-2018', '2017-10-31'),
(55, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Monthly', '11', 'Paid', '30-00-2018', '2017-10-31'),
(56, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Monthly', '-11', 'Paid', '30-01-2018', '2017-10-31'),
(57, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Weekly', 'testdialogbill', 'Paid', '22-11-2017', '2017-11-12'),
(58, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Weekly', 'testdialog', 'Paid', '23-11-2017', '2017-11-12'),
(59, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Weekly', 'dialog', 'Unpaid', '25-11-2017', 'Unpaid'),
(60, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', 'Weekly', 'dialogka', 'Paid', '24-11-2017', '2017-11-12'),
(63, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Monthly', 'phone', 'Paid', '08-12-2017', '2017-11-12'),
(64, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', 'Weekly', 'หนี้', 'Paid', '27-12-2017', '2017-11-12'),
(65, '02tfWQmgJVfQQiPgm2z9sPKvfK32', 'Monthly', 'iPhone8', 'Unpaid', '03-04-2018', 'Unpaid'),
(66, '02tfWQmgJVfQQiPgm2z9sPKvfK32', 'Monthly', 'water', 'Unpaid', '05-12-2017', 'Unpaid'),
(67, '1NzulQpFxYadvpJsgXn3dX2dxLw2', 'Monthly', 'water', 'Paid', '30-12-2017', '2017-11-22'),
(68, '1NzulQpFxYadvpJsgXn3dX2dxLw2', 'Monthly', 'fire', 'Paid', '22-12-2017', '2017-11-13'),
(69, 'YAzv82iDRbYVJerjYzO0ZsFtl8e2', 'Monthly', 'water', 'Paid', '20-01-2018', '2017-11-27');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transaction_id` int(10) NOT NULL,
  `cust_id` char(255) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(510) NOT NULL,
  `cost` double NOT NULL,
  `transaction` char(255) NOT NULL,
  `category` char(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transaction_id`, `cust_id`, `timestamp`, `description`, `cost`, `transaction`, `category`) VALUES
(8, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-29 05:54:22', 'from mom', 5893, 'Income', 'Salary'),
(9, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-29 05:56:15', 'grab', 234, 'Expense', 'Transport'),
(12, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-29 06:24:06', 'Uber', 234, 'Expense', 'Transport'),
(24, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-29 07:04:26', 'gift', 777, 'Income', 'Gift'),
(25, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-29 07:06:35', 'from mom', 77, 'Income', 'Family and Personal'),
(26, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-29 07:07:26', 'gift', 77, 'Income', 'Gift'),
(27, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-08-29 07:08:26', 'weekly', 88, 'Income', 'Salary'),
(28, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-29 07:09:52', 'weekly', 888, 'Income', 'Salary'),
(38, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-29 10:37:08', 'egg', 22, 'Expense', 'Food and Drink'),
(44, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-29 12:22:17', 'food', 231, 'Expense', 'Food and Drink'),
(59, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-30 08:11:53', 'taxi', 345, 'Expense', 'Transport'),
(60, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-30 08:47:48', 'taxi', 3456, 'Expense', 'Transport'),
(61, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-30 08:52:59', 'rice', 546, 'Expense', 'Food and Drink'),
(62, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-30 08:55:31', 'snack', 422, 'Expense', 'Food and Drink'),
(63, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-30 08:58:05', 'housing', 3000, 'Expense', 'Family and Personal'),
(64, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-30 08:58:39', 'housing', 3000, 'Expense', 'Family and Personal'),
(65, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-30 08:59:36', 'housing', 2000, 'Expense', 'Family and Personal'),
(66, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-30 09:01:29', 'housing', 1000, 'Expense', 'Family and Personal'),
(73, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-30 11:09:56', 'taxi', 87678, 'Expense', 'Transport'),
(93, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-08-30 13:10:50', 'bonus', 34059, 'Income', 'Gift'),
(172, '0Y9ef0KCOWMKNgtgQz4HYA8QmD32', '2017-09-01 12:04:19', 'mobile', 456245, 'Income', 'Gift'),
(173, '0Y9ef0KCOWMKNgtgQz4HYA8QmD32', '2017-09-01 12:09:59', 'ham', 534, 'Expense', 'Food and Drink'),
(174, '0Y9ef0KCOWMKNgtgQz4HYA8QmD32', '2017-09-01 12:10:25', 'ham', 45, 'Expense', 'Food and Drink'),
(178, '0Y9ef0KCOWMKNgtgQz4HYA8QmD32', '2017-09-01 12:37:42', 'money', 87, 'Income', 'Gift'),
(179, '0Y9ef0KCOWMKNgtgQz4HYA8QmD32', '2017-09-01 12:38:09', 'gift', 56465, 'Income', 'Gift'),
(180, '0Y9ef0KCOWMKNgtgQz4HYA8QmD32', '2017-09-01 12:40:32', 'meat', 435, 'Expense', 'Food and Drink'),
(181, '0Y9ef0KCOWMKNgtgQz4HYA8QmD32', '2017-09-01 12:57:40', 'shabu', 344, 'Expense', 'Food and Drink'),
(184, '0Y9ef0KCOWMKNgtgQz4HYA8QmD32', '2017-09-01 13:01:45', 'burger', 34, 'Expense', 'Food and Drink'),
(185, '0Y9ef0KCOWMKNgtgQz4HYA8QmD32', '2017-09-01 13:04:15', 'pen', 5, 'Expense', 'Education'),
(187, '0Y9ef0KCOWMKNgtgQz4HYA8QmD32', '2017-09-01 13:11:53', 'food', 345, 'Expense', 'Food and Drink'),
(192, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-09-02 08:05:26', 'shirt', 100, 'Expense', 'Shopping'),
(215, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-09-02 12:41:15', 'jam', 545, 'Expense', 'Food and Drink'),
(232, 'W5zo4Ie5QtTWwb97YirzWMZh9wT2', '2017-09-04 04:37:41', 'shirt', 100, 'Expense', 'Shopping'),
(234, 'W5zo4Ie5QtTWwb97YirzWMZh9wT2', '2017-09-04 13:34:05', 'egg', 100, 'Expense', 'Food and Drink'),
(235, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-09-04 13:38:59', 'bird', 2000, 'Expense', 'Shopping'),
(236, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-09-04 13:42:37', 'egg', 20, 'Expense', 'Food and Drink'),
(237, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-09-04 13:42:49', 'mom', 20000, 'Income', 'Salary'),
(239, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-09-04 13:57:45', 'dad', 5000, 'Income', 'Gift'),
(240, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-09-04 20:15:30', 'gold', 2345, 'Income', 'Gift'),
(241, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-09-04 20:17:04', 'me', 34534, 'Income', 'Extra income'),
(242, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-09-04 20:18:52', 'me', 3453, 'Income', 'Family and Personal'),
(246, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-09-05 07:54:54', 'gift', 6546, 'Income', 'Gift'),
(253, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-09-06 05:13:52', 'from mom', 1000, 'Income', 'Salary'),
(254, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-09-06 08:17:51', 'mom', 200, 'Income', 'Extra income'),
(255, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-09-06 08:18:15', 'mom', 300, 'Income', 'Extra income'),
(256, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-09-06 08:25:07', 'bank', 1000, 'Expense', 'Saving and Investment'),
(262, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-09-07 09:51:41', ' shirt', 200, 'Expense', 'Shopping'),
(265, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-09-07 16:17:20', 'shirt', 200.5, 'Expense', 'Shopping'),
(277, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-09-09 14:27:11', 'dad', 1000, 'Income', 'Family and Personal'),
(283, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-09-11 16:30:08', 'loan', 200, 'Income', 'Loan'),
(293, 'O27fRZ9UWCcIr0QvgQ1nMuDyLz92', '2017-09-14 09:08:14', 'Salary', 100000, 'Income', 'Salary'),
(321, 'dFqwZ1x5slXHypVOSLgXSzSUJGp1', '2017-09-22 11:11:41', 'การบูน', 200, 'Expense', 'Family and Personal'),
(326, 'dFqwZ1x5slXHypVOSLgXSzSUJGp1', '2017-09-22 11:50:49', 'goal', 200000, 'Income', 'Gift'),
(327, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-09-22 12:32:32', 'rice', 300, 'Expense', 'Food and Drink'),
(328, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-09-22 17:16:03', 'mom', 2000, 'Income', 'Gift'),
(337, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-09-25 06:56:46', 'ข้าวกะเพรา 30 บาท', 30, 'Expense', 'Food and Drink'),
(338, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-09-25 08:16:17', 'drink', 40, 'Expense', 'Food and Drink'),
(339, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-09-25 08:18:39', 'วันนี้กินข้าวมา 50 บาท', 50, 'Expense', 'Food and Drink'),
(340, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-09-25 08:52:11', 'วันนี้กินข้าวมา 50 บาท', 50, 'Expense', 'Food and Drink'),
(350, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-11 09:42:05', 'dad', 2000, 'Income', 'Extra income'),
(352, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-12 17:01:11', 'rice', 300, 'Expense', 'Food and Drink'),
(354, '9hG3lYvNswNqe19X3wrOB5GmRqj1', '2017-10-16 01:25:49', 'คุณพ่อให้เงินมา5000', 5000, 'Expense', 'Food and Drink'),
(356, '9hG3lYvNswNqe19X3wrOB5GmRqj1', '2017-10-16 01:26:21', 'asset', 20000, 'Income', 'Loan'),
(357, '9hG3lYvNswNqe19X3wrOB5GmRqj1', '2017-10-16 12:34:00', 'แอมป์ซื้อข้าวผัดกระเพรา 120 บาท', 120, 'Expense', 'Food and Drink'),
(358, '9hG3lYvNswNqe19X3wrOB5GmRqj1', '2017-10-16 12:52:05', 'ข้าว 2000', 2000, 'Expense', 'Food and Drink'),
(359, '9hG3lYvNswNqe19X3wrOB5GmRqj1', '2017-10-18 11:37:50', 'Drinking water KMUTT', 6, 'Expense', 'Food and Drink'),
(360, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-25 07:18:57', 'test', 24, 'Income', 'Salary'),
(361, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-25 07:21:04', 'eiei', 34, 'Expense', 'Saving and Investment'),
(362, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-25 07:22:14', 'gg', 32235, 'Expense', 'Family and Personal'),
(363, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-25 07:23:14', 'kl', 4354, 'Income', 'Salary'),
(364, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-25 10:36:37', 'dad', 2000, 'Income', 'Gift'),
(365, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-25 10:41:27', 'dad', 300, 'Income', 'Loan'),
(366, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-25 10:42:04', 'pleum', 2000, 'Income', 'Loan'),
(367, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-25 10:53:33', 'grab', 300, 'Expense', 'Transport'),
(368, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-25 10:57:16', 'hospital', 3000, 'Expense', 'Healthcare'),
(369, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-25 11:05:39', 'karaoke', 350, 'Expense', 'Entertainment'),
(370, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-25 11:07:14', 'TMB', 2000, 'Expense', 'Saving and Investment'),
(371, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-25 11:07:41', 'mom', 20000, 'Income', 'Extra income'),
(372, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2016-10-25 11:08:07', 'clothes', 2000, 'Expense', 'Family and Personal'),
(392, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-30 05:27:55', 'vita', 180, 'Expense', 'Food and Drink'),
(393, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-30 05:34:01', 'via', 500, 'Expense', 'Food and Drink'),
(394, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-30 05:39:29', 'Veta Berry 42ml', 35, 'Expense', 'Food and Drink'),
(395, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-30 05:40:03', 'Veta Berry 42ml', 35, 'Expense', 'Food and Drink'),
(396, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-30 05:42:58', 'Veta Berry 42ml', 35, 'Expense', 'Food and Drink'),
(397, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2016-10-30 06:23:47', 'Veta Berry 42ml', 35, 'Expense', 'Food and Drink'),
(400, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-30 08:23:18', 'อิชิตันกรีนที ชาเขียวเก๊กฮวย 420ml', 16, 'Expense', 'Food and Drink'),
(402, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2016-10-30 08:36:59', 'Veta Berry 42ml', 35, 'Expense', 'Food and Drink'),
(403, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-10-30 10:02:18', 'อิชิตันกรีนที ชาเขียวเก๊กฮวย 420ml', 16, 'Expense', 'Food and Drink'),
(404, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2016-10-30 10:03:27', 'Veta Berry 42ml', 35, 'Expense', 'Food and Drink'),
(428, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-31 07:01:31', 'Japan', 123, 'Expense', 'Bill'),
(429, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-31 07:04:29', 'teset', 123, 'Expense', 'Bill'),
(430, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-31 07:09:20', '6m', 123, 'Expense', 'Bill'),
(434, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-31 07:15:43', '11month', 123, 'Expense', 'Bill'),
(435, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-31 07:17:16', '11', 123, 'Expense', 'Bill'),
(436, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-31 07:19:37', '-11', 123, 'Expense', 'Bill'),
(437, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-10-31 07:20:02', 'testdialogbill', 123, 'Expense', 'Bill'),
(438, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-06 04:50:10', 'mommy', 2000, 'Income', 'Salary'),
(440, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-06 05:25:25', 'dialog', 123, 'Expense', 'Bill'),
(441, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-06 09:21:34', 'testyear', 200, 'Expense', 'Bill'),
(449, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-09 03:54:35', 'TRUE', 200, 'Expense', 'Bill'),
(453, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-09 05:49:45', 'แม่ให้', 3000, 'Income', 'Gift'),
(455, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-10 09:27:43', 'ซื้อนม 20 บาท', 20, 'Expense', 'Food and Drink'),
(459, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-10 09:36:21', 'ซื้อขนม 30 บาท', 30, 'Expense', 'Food and Drink'),
(474, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-12 04:29:17', 'pen', 20, 'Expense', 'Education'),
(483, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-12 04:45:37', 'ซื้อโกโก้ 50 บาท', 50, 'Expense', 'Food and Drink'),
(485, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-12 05:01:12', 'Imperial Butter Cookies', 20, 'Expense', 'Food and Drink'),
(524, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-11-12 09:25:26', 'salary', 3000, 'Income', 'Salary'),
(525, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-11-12 09:25:47', 'yoyo', 40, 'Expense', 'Food and Drink'),
(530, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-11-12 09:27:16', 'dad', 12314, 'Income', 'Gift'),
(531, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-13 02:22:01', 'milk', 15, 'Expense', 'Food and Drink'),
(533, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-13 02:23:04', 'Lays Stax Original', 42, 'Expense', 'Food and Drink'),
(534, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 02:49:36', 'ซื้อตะกร้า 20 บาท', 20, 'Expense', 'Shopping'),
(537, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-13 03:32:59', 'mulk', 15, 'Expense', 'Shopping'),
(545, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-11-13 04:25:15', 'toy', 20, 'Expense', 'Shopping'),
(546, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-13 04:26:41', 'Halls Honey-Lemon', 10, 'Expense', 'Food and Drink'),
(547, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 04:27:19', 'cocoa 20', 20, 'Expense', 'Food and Drink'),
(553, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-13 04:34:48', 'Oreo Thins Vanilla Delight', 35, 'Expense', 'Food and Drink'),
(554, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-13 04:39:57', 'ซื้อหนังสือ 50 บาท', 50, 'Expense', 'Education'),
(555, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-13 04:40:21', 'Oreo Thins Vanilla Delight', 35, 'Expense', 'Food and Drink'),
(558, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-13 04:44:23', 'game', 1200, 'Expense', 'Family and Personal'),
(560, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-13 04:45:10', 'Collon Thai Tea', 16, 'Expense', 'Food and Drink'),
(561, 'gHlQfMphHLXBWQ8gF5xkZH5XK7T2', '2017-11-13 04:49:53', 'Halls Honey-Lemon', 10, 'Expense', 'Food and Drink'),
(565, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 05:12:21', 'toy', 20, 'Expense', 'Shopping'),
(566, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 05:12:47', 'Nissin Chilli Noodles Tom Yum Shrimp', 15, 'Expense', 'Food and Drink'),
(574, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 05:25:35', 'Oreo Thins Vanilla Delight', 35, 'Expense', 'Food and Drink'),
(577, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 05:30:42', 'ซื้อหนังสือ 50 บาท', 50, 'Expense', 'Education'),
(578, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 05:31:00', 'Imperial Butter Cookies', 20, 'Expense', 'Food and Drink'),
(579, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 05:35:22', 'ซื้อหนังสือ 50 บาท', 50, 'Expense', 'Education'),
(610, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 08:31:43', 'ซื้อโกโก้ 20 บาท', 20, 'Expense', 'Food and Drink'),
(611, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 08:32:07', 'EZYGO ข้าวผัดปู', 35, 'Expense', 'Food and Drink'),
(612, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 08:36:22', 'ผช', 500, 'Expense', 'Family and Personal'),
(613, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 08:36:54', 'ซื้อหนังสือ 50 บาท', 50, 'Expense', 'Education'),
(614, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 08:37:41', 'Lays Stax Original', 42, 'Expense', 'Food and Drink'),
(615, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-13 08:37:46', 'Lays Stax Original', 42, 'Expense', 'Food and Drink'),
(616, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-11-15 07:58:09', 'aaaaa', 1000, 'Income', 'Family and Personal'),
(617, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-11-15 07:58:50', 'sssssss', 2000, 'Income', 'Salary'),
(618, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-11-17 08:24:12', 'rice', 3000, 'Expense', 'Food and Drink'),
(619, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-11-17 08:31:49', 'dad', 2000, 'Income', 'Extra income'),
(620, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-11-17 08:32:09', 'amp', 200, 'Income', 'Loan'),
(621, '02tfWQmgJVfQQiPgm2z9sPKvfK32', '2017-11-17 08:58:18', 'mom', 200, 'Income', 'Loan'),
(622, 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1', '2017-11-20 04:42:24', 'jdjl20 ', 20, 'Income', 'Gift'),
(623, '1NzulQpFxYadvpJsgXn3dX2dxLw2', '2017-11-22 02:16:08', 'water', 12, 'Expense', 'Bill'),
(624, 'YAzv82iDRbYVJerjYzO0ZsFtl8e2', '2017-11-27 04:18:40', 'salary', 10000, 'Income', 'Salary'),
(625, 'YAzv82iDRbYVJerjYzO0ZsFtl8e2', '2017-11-27 04:18:56', 'drink', 500, 'Expense', 'Food and Drink'),
(626, 'YAzv82iDRbYVJerjYzO0ZsFtl8e2', '2017-11-27 04:20:45', 'ซื้อโกโก้ 20 บาท', 20, 'Expense', 'Food and Drink'),
(627, 'YAzv82iDRbYVJerjYzO0ZsFtl8e2', '2017-11-27 04:21:17', 'Scott Interfold Tissue', 14, 'Expense', 'Healthcare'),
(628, 'YAzv82iDRbYVJerjYzO0ZsFtl8e2', '2017-11-27 04:25:07', 'water', 500, 'Expense', 'Bill'),
(629, 'YAzv82iDRbYVJerjYzO0ZsFtl8e2', '2017-11-27 04:27:19', 'water', 200, 'Expense', 'Bill');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cust_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `goal`
--
ALTER TABLE `goal`
  ADD PRIMARY KEY (`goal_id`,`cust_id`),
  ADD KEY `cust_id` (`cust_id`);

--
-- Indexes for table `period`
--
ALTER TABLE `period`
  ADD PRIMARY KEY (`bill_id`,`cust_id`),
  ADD KEY `cust_id` (`cust_id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transaction_id`,`cust_id`),
  ADD KEY `cust_id` (`cust_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `goal`
--
ALTER TABLE `goal`
  MODIFY `goal_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;
--
-- AUTO_INCREMENT for table `period`
--
ALTER TABLE `period`
  MODIFY `bill_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;
--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transaction_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=630;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `period`
--
ALTER TABLE `period`
  ADD CONSTRAINT `period_ibfk_1` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
