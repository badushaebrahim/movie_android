-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 19, 2022 at 07:14 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `moviez`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_login`
--

CREATE TABLE `tbl_login` (
  `lid` int(11) NOT NULL,
  `uid` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `utype` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_movies`
--

CREATE TABLE `tbl_movies` (
  `eid` int(11) NOT NULL,
  `mimg` longblob NOT NULL,
  `mname` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `director` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `star` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `tid` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `language` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_price`
--

CREATE TABLE `tbl_price` (
  `pid` int(11) NOT NULL,
  `tid` varchar(100) NOT NULL,
  `mid` varchar(100) NOT NULL,
  `price` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_register`
--

CREATE TABLE `tbl_register` (
  `uid` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_show`
--

CREATE TABLE `tbl_show` (
  `sid` int(11) NOT NULL,
  `eid` varchar(100) NOT NULL,
  `time` varchar(100) NOT NULL,
  `uid` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_theater`
--

CREATE TABLE `tbl_theater` (
  `mid` int(11) NOT NULL,
  `tname` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `scap` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `taddress` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `tphone` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_theatermovie`
--

CREATE TABLE `tbl_theatermovie` (
  `thid` int(11) NOT NULL,
  `tid` varchar(100) NOT NULL,
  `eid` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ticket`
--

CREATE TABLE `tbl_ticket` (
  `ticket_id` int(11) NOT NULL,
  `mname` varchar(100) NOT NULL,
  `tname` varchar(100) NOT NULL,
  `time` varchar(100) NOT NULL,
  `bdate` varchar(100) NOT NULL,
  `numticket` varchar(100) NOT NULL,
  `amount` varchar(100) NOT NULL,
  `uid` varchar(100) NOT NULL,
  `tid` varchar(100) NOT NULL,
  `mpic` longblob NOT NULL,
  `qrcode` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_login`
--
ALTER TABLE `tbl_login`
  ADD PRIMARY KEY (`lid`);

--
-- Indexes for table `tbl_movies`
--
ALTER TABLE `tbl_movies`
  ADD PRIMARY KEY (`eid`);

--
-- Indexes for table `tbl_price`
--
ALTER TABLE `tbl_price`
  ADD PRIMARY KEY (`pid`);

--
-- Indexes for table `tbl_register`
--
ALTER TABLE `tbl_register`
  ADD PRIMARY KEY (`uid`);

--
-- Indexes for table `tbl_show`
--
ALTER TABLE `tbl_show`
  ADD PRIMARY KEY (`sid`);

--
-- Indexes for table `tbl_theater`
--
ALTER TABLE `tbl_theater`
  ADD PRIMARY KEY (`mid`);

--
-- Indexes for table `tbl_theatermovie`
--
ALTER TABLE `tbl_theatermovie`
  ADD PRIMARY KEY (`thid`);

--
-- Indexes for table `tbl_ticket`
--
ALTER TABLE `tbl_ticket`
  ADD PRIMARY KEY (`ticket_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_login`
--
ALTER TABLE `tbl_login`
  MODIFY `lid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_movies`
--
ALTER TABLE `tbl_movies`
  MODIFY `eid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_price`
--
ALTER TABLE `tbl_price`
  MODIFY `pid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_register`
--
ALTER TABLE `tbl_register`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_show`
--
ALTER TABLE `tbl_show`
  MODIFY `sid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_theater`
--
ALTER TABLE `tbl_theater`
  MODIFY `mid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_theatermovie`
--
ALTER TABLE `tbl_theatermovie`
  MODIFY `thid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_ticket`
--
ALTER TABLE `tbl_ticket`
  MODIFY `ticket_id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
