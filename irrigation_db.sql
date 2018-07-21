-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 20, 2018 at 06:26 PM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `irrigation_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `cure_tb`
--

CREATE TABLE `cure_tb` (
  `id` int(10) UNSIGNED NOT NULL,
  `disease_id` int(10) UNSIGNED NOT NULL DEFAULT '0',
  `cure_steps` varchar(2000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cure_tb`
--

INSERT INTO `cure_tb` (`id`, `disease_id`, `cure_steps`) VALUES
(1, 1, 'cut the soil near the potted plant and kill the insects.'),
(2, 1, 'Irrigation of water mixed with kerosene (at 2-3 lit / ha).'),
(3, 1, 'Putting stalks in the field for birding.'),
(4, 1, 'Loosen the field soil.'),
(5, 2, 'If the worm is out when the soil is removed from the root of the tree, then kill it.'),
(6, 2, 'Irrigation of kerosene mixed water.'),
(7, 2, 'Putting stalks in the field for birding.'),
(8, 2, 'The use of chemical pesticides is not economically profitable'),
(9, 3, 'Apply 2.5 kg TSP fertilizer per bigha. Mix the fertilizer well with the soil.'),
(10, 4, 'In the morning, cut the soil near the potted plant and kill the insects.'),
(11, 4, 'Irrigation of water mixed with kerosene (at 2-3 lit / ha).'),
(12, 4, 'Putting stalks in the field for birding.'),
(13, 4, 'Loosen the field soil.'),
(14, 5, 'Collection of insects by the hair mesh.'),
(15, 5, 'Clean clean cultivation.'),
(16, 5, 'Covering the seedlings with nets'),
(17, 5, 'Spray the 0.5% density soap water or 5 ml of liquid soap mixed with water.'),
(18, 5, 'spray 500 grams of neem seeds in 10 liters of water by spraying it in 12 hours.'),
(19, 6, 'Within a week of sapling, the firma trap will be on the ground.'),
(20, 6, 'remove the pests from the tree and crush them with feet'),
(21, 6, 'hold down the large insects worn by them.'),
(22, 6, 'Thus, the insects can be easily suppressed.'),
(23, 7, 'Clear water applied to dry ash in early stages'),
(24, 7, 'Keep sprayed areas clean'),
(25, 7, 'Use a yellow color trap.'),
(26, 7, 'Tobacco powder (10 grams), Saban powder (5 gram) and nem leaf extract mixed with water per liter'),
(27, 7, 'If there are more than 50 insects per tree, spray the spray of Admeer 20 SL 0.5 ml / lea water.'),
(28, 8, 'In the morning, cut the soil near the potted plant and kill the insects.'),
(29, 8, 'Irrigation of water mixed with kerosene (at 2-3 lit / ha).'),
(30, 8, 'Putting stalks in the field for birding.'),
(31, 8, 'Loosen the field soil.'),
(32, 9, 'Collect and destroy the affected leaves and destroy them.'),
(33, 9, 'Place the glue yellow trap.'),
(34, 9, 'Saipermethrine group insecticides (eg COT 10 EC) 1 ml / Li Sprayed with water mixed with water.');

-- --------------------------------------------------------

--
-- Table structure for table `disease_tb`
--

CREATE TABLE `disease_tb` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) NOT NULL,
  `symptoms` varchar(1000) NOT NULL,
  `plant_name` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `disease_tb`
--

INSERT INTO `disease_tb` (`id`, `name`, `symptoms`, `plant_name`) VALUES
(1, 'Corncorn insect', 'The insect cut off the soil at the plant during the night.The plants are seen lying on the ground. ', 'Sweet Corn'),
(2, 'Corn Straw Sprinkler', ' At the beginning of the insect of the insect, the strawberries go inside and eat. The trees die dry and die. After flowering, the flowers with stems dried and killed.', 'Sweet Corn'),
(3, 'Phosphorus fertilizer deficit', 'The leaves contain purple colors. ', 'Sweet Corn'),
(4, 'Cauliflower ketchup', 'During the night of the insect, the seedlings cut off the soil. In the morning, the plants are seen lying on the ground. ', 'Cauliflower'),
(5, 'Cauliflower Flat Beetle Pest', 'Both the elderly and the child are harmed. Older adults suffer more than planting. They eat small leaves in small pores. There are numerous holes in the affected pages.', 'Cauliflower'),
(6, 'Tobacco Caterpillars of Cauliflower', 'The \n\ninsects leave the egg from the eggs together on the sheet, and the green part of the leaf starts to grow bigger. Due to this, many insects have been found on leaves which are similar to leaf mesh. Within a few days they spread to the fields and drank the leaves by making big holes.', 'Cauliflower'),
(7, 'Potato pest', 'weaken the tree by consuming tender leaves and dragon juice of insect trees. Removal of plant affected area width', 'Potato'),
(8, 'Potato ketui insects', 'During the night of the insect, the seedlings cut off the soil. In the morning, the plants are seen lying on the ground. ', 'Potato'),
(9, 'leaf injection insect', 'The tiny insect consumes the green part of the leaves. So, the leaves on the leaves are like a bent line and the leaves get dried up.', 'Potato');

-- --------------------------------------------------------

--
-- Table structure for table `farmer_tb`
--

CREATE TABLE `farmer_tb` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `user_name` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `type` varchar(200) NOT NULL,
  `phone` varchar(50) NOT NULL DEFAULT '0',
  `email` varchar(50) DEFAULT '0',
  `address` varchar(50) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='All Farmers related information will be here';

--
-- Dumping data for table `farmer_tb`
--

INSERT INTO `farmer_tb` (`id`, `name`, `user_name`, `password`, `type`, `phone`, `email`, `address`) VALUES
(1, 'Neen', 'Neen', '123', 'Farmer', '0969464022', 'neen@gmail.com', 'dhaka'),
(2, 'hossain', 'hossain', '123', 'Admin', '01711979665', 'h@gmail.com', 'uttara'),
(3, 'Labonno', 'labonno', '123', 'Farmer', '01945678910', 'L@gmail.com', 'Mirpur'),
(4, 'tonni', 'tonni', '123', 'Farmer', '01732654987', 'tonni@gmail.com', 'ECB'),
(5, 'Masum', 'masum', '123', 'Farmer', '01623456874', 'masum@gmail.com', 'tongi');

-- --------------------------------------------------------

--
-- Table structure for table `personal_schedule_tb`
--

CREATE TABLE `personal_schedule_tb` (
  `id` int(10) UNSIGNED NOT NULL,
  `plant_id` int(10) UNSIGNED NOT NULL,
  `date` date NOT NULL,
  `work_complete_date` date DEFAULT NULL,
  `step_desc` varchar(2000) NOT NULL,
  `farmer_id` int(10) UNSIGNED DEFAULT NULL,
  `action` int(10) UNSIGNED NOT NULL DEFAULT '0',
  `start_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `personal_schedule_tb`
--

INSERT INTO `personal_schedule_tb` (`id`, `plant_id`, `date`, `work_complete_date`, `step_desc`, `farmer_id`, `action`, `start_date`) VALUES
(1, 1, '2018-04-20', '2018-04-20', 'Select a site with at least 6 hours of full sun.', 1, 1, '2018-04-20'),
(2, 1, '2018-04-20', '2018-04-20', 'add composted mature to the soil before planting', 1, 1, '2018-04-20'),
(3, 1, '2018-04-20', '2018-04-20', 'Test your soil! The soil pH should be between 6.5 and 6.8.', 1, 1, '2018-04-20'),
(4, 1, '2018-04-21', NULL, 'It is best to start cauliflower from transplants rather than seeds.', 1, 0, '2018-04-20'),
(5, 1, '2018-04-21', NULL, 'Transplant 2 to 4 weeks before the average frost date in the spring', 1, 0, '2018-04-20'),
(6, 1, '2018-04-26', NULL, 'Water the Transplant upto 1 to 1.5 inches', 1, 0, '2018-04-20'),
(7, 1, '2018-04-27', NULL, 'side-dress the plants with a nitrogen fertilizer.', 1, 0, '2018-04-20'),
(8, 1, '2018-05-03', NULL, 'Water The Transplant', 1, 0, '2018-04-20'),
(9, 1, '2018-05-10', NULL, 'Space the transplants 18 to 24 inches apart with 30 inches between rows.', 1, 0, '2018-04-20'),
(10, 1, '2018-05-10', NULL, 'Use starter fertilizer when transplanting.', 1, 0, '2018-04-20'),
(11, 1, '2018-05-19', NULL, 'Plant fall cauliflower about the same time as fall cabbage.', 1, 0, '2018-04-20'),
(12, 1, '2018-06-03', NULL, 'he cauliflower will start out as a loose head and that it takes time for the head to fully form.', 1, 0, '2018-04-20'),
(13, 1, '2018-06-18', NULL, 'When tthe white head is about 2 to 3 inches in diameter, tie the outer leaves together over the head with a rubber band', 1, 0, '2018-04-20'),
(14, 1, '2018-06-18', NULL, 'This is called blanching, protects the head from the sun,  helps you get that pretty white color.', 1, 0, '2018-04-20'),
(15, 1, '2018-06-28', NULL, 'The plants are usually ready for harvest 7 to 12 days after blanching.', 1, 0, '2018-04-20'),
(16, 2, '2018-04-20', '2018-04-20', 'Work in aged manure or compost the fall before planting and let over winter in the soil.', 1, 1, '2018-04-20'),
(17, 2, '2018-04-20', '2018-04-20', 'Plant seeds outdoors two weeks after the last spring frost date.', 1, 1, '2018-04-20'),
(18, 2, '2018-04-20', '2018-04-20', 'Make sure soil temperature is above 60 degrees F for successful germination.', 1, 1, '2018-04-20'),
(19, 2, '2018-04-20', '2018-04-20', 'In colder zones, the ground can be warmed by a black plastic cover if necessary.', 1, 1, '2018-04-20'),
(20, 2, '2018-04-22', NULL, 'Plant seeds 1 inch deep and 4 to 6 inches apart. Rows 30 to 36 inches apart.', 1, 0, '2018-04-20'),
(21, 2, '2018-04-29', NULL, 'You may choose to fertilize at planting time; corn is meant to grow rapidly.', 1, 0, '2018-04-20'),
(22, 2, '2018-04-29', NULL, 'If you are confident that the soil is adequate, this can be skipped.', 1, 0, '2018-04-20'),
(23, 2, '2018-05-04', NULL, 'When your plants are 3 to 4 inches tall, thin them so they are 8 to 12 inches apart.', 1, 0, '2018-04-20'),
(24, 2, '2018-05-09', NULL, 'Be careful not to damage the roots when weeding.', 1, 0, '2018-04-20'),
(25, 2, '2018-05-12', NULL, 'Soil must be well drained and able to keep consistent moisture.', 1, 0, '2018-04-20'),
(26, 2, '2018-05-12', NULL, 'In dry conditions, be sure to keep corn well watered due to its shallow roots.', 1, 0, '2018-04-20'),
(27, 2, '2018-05-13', NULL, 'Water at a rate of 5 gallons per sq yard.', 1, 0, '2018-04-20'),
(28, 2, '2018-05-19', NULL, 'Harvest when tassels begin to turn brown and cobs start to swell.', 1, 0, '2018-04-20'),
(29, 2, '2018-05-19', NULL, 'Pull ears downward and twist to take off stalk.', 1, 0, '2018-04-20'),
(30, 2, '2018-05-24', NULL, 'Prepare for eating or preserving immediately after picking.', 1, 0, '2018-04-20'),
(31, 2, '2018-05-24', NULL, 'Sweet corn freezes well, especially if removed from ears before freezing. Learn how to properly freeze corn.', 1, 0, '2018-04-20'),
(32, 1, '2018-04-20', NULL, 'Select a site with at least 6 hours of full sun.', 4, 0, '2018-04-20'),
(33, 1, '2018-04-20', NULL, 'add composted mature to the soil before planting', 4, 0, '2018-04-20'),
(34, 1, '2018-04-20', NULL, 'Test your soil! The soil pH should be between 6.5 and 6.8.', 4, 0, '2018-04-20'),
(35, 1, '2018-04-21', NULL, 'It is best to start cauliflower from transplants rather than seeds.', 4, 0, '2018-04-20'),
(36, 1, '2018-04-21', NULL, 'Transplant 2 to 4 weeks before the average frost date in the spring', 4, 0, '2018-04-20'),
(37, 1, '2018-04-26', NULL, 'Water the Transplant upto 1 to 1.5 inches', 4, 0, '2018-04-20'),
(38, 1, '2018-04-27', NULL, 'side-dress the plants with a nitrogen fertilizer.', 4, 0, '2018-04-20'),
(39, 1, '2018-05-03', NULL, 'Water The Transplant', 4, 0, '2018-04-20'),
(40, 1, '2018-05-10', NULL, 'Space the transplants 18 to 24 inches apart with 30 inches between rows.', 4, 0, '2018-04-20'),
(41, 1, '2018-05-10', NULL, 'Use starter fertilizer when transplanting.', 4, 0, '2018-04-20'),
(42, 1, '2018-05-19', NULL, 'Plant fall cauliflower about the same time as fall cabbage.', 4, 0, '2018-04-20'),
(43, 1, '2018-06-03', NULL, 'he cauliflower will start out as a loose head and that it takes time for the head to fully form.', 4, 0, '2018-04-20'),
(44, 1, '2018-06-18', NULL, 'When tthe white head is about 2 to 3 inches in diameter, tie the outer leaves together over the head with a rubber band', 4, 0, '2018-04-20'),
(45, 1, '2018-06-18', NULL, 'This is called blanching, protects the head from the sun,  helps you get that pretty white color.', 4, 0, '2018-04-20'),
(46, 1, '2018-06-28', NULL, 'The plants are usually ready for harvest 7 to 12 days after blanching.', 4, 0, '2018-04-20');

-- --------------------------------------------------------

--
-- Stand-in structure for view `personal_schedule_tb_v`
-- (See below for the actual view)
--
CREATE TABLE `personal_schedule_tb_v` (
`id` int(10) unsigned
,`plant_id` int(10) unsigned
,`date` date
,`start_date` date
,`step_desc` varchar(2000)
,`farmer_id` int(10) unsigned
,`action` int(10) unsigned
,`plant_name` varchar(50)
,`description` varchar(1500)
);

-- --------------------------------------------------------

--
-- Table structure for table `plant_setup_chd_tb`
--

CREATE TABLE `plant_setup_chd_tb` (
  `id` int(11) UNSIGNED NOT NULL,
  `plant_id` int(11) UNSIGNED DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `steps_desc` varchar(2000) DEFAULT NULL,
  `days` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='All steps required for cultivating plant ';

--
-- Dumping data for table `plant_setup_chd_tb`
--

INSERT INTO `plant_setup_chd_tb` (`id`, `plant_id`, `priority`, `steps_desc`, `days`) VALUES
(1, 1, NULL, 'Select a site with at least 6 hours of full sun.', 1),
(2, 1, NULL, 'add composted mature to the soil before planting', 1),
(3, 1, NULL, 'Test your soil! The soil pH should be between 6.5 and 6.8.', 1),
(4, 1, NULL, 'It is best to start cauliflower from transplants rather than seeds.', 2),
(5, 1, NULL, 'Transplant 2 to 4 weeks before the average frost date in the spring', 2),
(6, 1, NULL, 'Water the Transplant upto 1 to 1.5 inches', 7),
(7, 1, NULL, 'side-dress the plants with a nitrogen fertilizer.', 8),
(8, 1, NULL, 'Water The Transplant', 14),
(9, 1, NULL, 'Space the transplants 18 to 24 inches apart with 30 inches between rows.', 21),
(10, 1, NULL, 'Use starter fertilizer when transplanting.', 21),
(11, 1, NULL, 'Plant fall cauliflower about the same time as fall cabbage.', 30),
(12, 1, NULL, 'he cauliflower will start out as a loose head and that it takes time for the head to fully form.', 45),
(13, 1, NULL, 'When tthe white head is about 2 to 3 inches in diameter, tie the outer leaves together over the head with a rubber band', 60),
(14, 1, NULL, 'This is called blanching, protects the head from the sun,  helps you get that pretty white color.', 60),
(15, 1, NULL, 'The plants are usually ready for harvest 7 to 12 days after blanching.', 70),
(16, 2, NULL, 'Work in aged manure or compost the fall before planting and let over winter in the soil.', 1),
(17, 2, NULL, 'Plant seeds outdoors two weeks after the last spring frost date.', 1),
(18, 2, NULL, 'Make sure soil temperature is above 60 degrees F for successful germination.', 1),
(19, 2, NULL, 'In colder zones, the ground can be warmed by a black plastic cover if necessary.', 1),
(20, 2, NULL, 'Plant seeds 1 inch deep and 4 to 6 inches apart. Rows 30 to 36 inches apart.', 3),
(21, 2, NULL, 'You may choose to fertilize at planting time; corn is meant to grow rapidly.', 10),
(22, 2, NULL, 'If you are confident that the soil is adequate, this can be skipped.', 10),
(23, 2, NULL, 'When your plants are 3 to 4 inches tall, thin them so they are 8 to 12 inches apart.', 15),
(24, 2, NULL, 'Be careful not to damage the roots when weeding.', 20),
(25, 2, NULL, 'Soil must be well drained and able to keep consistent moisture.', 23),
(26, 2, NULL, 'In dry conditions, be sure to keep corn well watered due to its shallow roots.', 23),
(27, 2, NULL, 'Water at a rate of 5 gallons per sq yard.', 24),
(28, 2, NULL, 'Harvest when tassels begin to turn brown and cobs start to swell.', 30),
(29, 2, NULL, 'Pull ears downward and twist to take off stalk.', 30),
(30, 2, NULL, 'Prepare for eating or preserving immediately after picking.', 35),
(31, 2, NULL, 'Sweet corn freezes well, especially if removed from ears before freezing. Learn how to properly freeze corn.', 35),
(32, 3, NULL, 'Plant 0 to 2 weeks after last spring frost.', 1),
(33, 3, NULL, 'Spread and mix in rotted manure or organic compost in the bottom of the trench.', 2),
(34, 3, NULL, 'Use knife to cut large tubers into pieces that are roughly 1 to 4 ounces each', 2),
(35, 3, NULL, 'making sure that there is at least one eye on each piece', 2),
(36, 3, NULL, 'cutting up potato pieces for planting, do so 1 to 2 days ahead of time.', 7),
(37, 3, NULL, 'help themto “heal” and form a protective layer', 7),
(38, 3, NULL, 'With a hoe or round-point shovel, dig a trench', 14),
(39, 3, NULL, '6 inches wide and 8 inches deep, tapering the bottom to about 3 inches wide.', 21),
(40, 3, NULL, 'Put a seed potato piece, cut side down, every 14 inches and cover with 3 to 4 inches of soil.', 21),
(41, 3, NULL, 'when sprouts appear, use a hoe to gently fill in the trench', 35),
(42, 3, NULL, 'leaving the soil mounded up 4 to 5 inches above ground level', 43),
(43, 3, NULL, 'repeat leaving the soil mounded up 4 to 5 inches above ground level', 50),
(44, 3, NULL, 'Repeat leaving the soil mounded up 4 to 5 inches above ground level', 60);

-- --------------------------------------------------------

--
-- Table structure for table `plant_setup_mst_tb`
--

CREATE TABLE `plant_setup_mst_tb` (
  `id` int(10) UNSIGNED NOT NULL,
  `plant_name` varchar(50) NOT NULL DEFAULT '0',
  `description` varchar(1500) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Plant name will be setup here';

--
-- Dumping data for table `plant_setup_mst_tb`
--

INSERT INTO `plant_setup_mst_tb` (`id`, `plant_name`, `description`) VALUES
(1, 'Cauliflower', 'Botanical Name	Brassica oleracea\nPlant Type	Vegetable\nSun Exposure	Full Sun\nSoil Type	Loamy\nSoil pH	Slightly Acidic to Neutral\nBloom Time	Spring\nFlower Color	White'),
(2, 'Sweet Corn', 'Botanical Name	Zea mays\nPlant Type	Vegetable\nSun Exposure	Full Sun\nSoil Type	Loamy\nSoil pH	Neutral'),
(3, 'Potato', 'Botanical Name	Solanum tuberosum\nPlant Type	Vegetable\nSun Exposure	Full Sun\nSoil Type	Sandy\nSoil pH	Acidic');

-- --------------------------------------------------------

--
-- Structure for view `personal_schedule_tb_v`
--
DROP TABLE IF EXISTS `personal_schedule_tb_v`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `personal_schedule_tb_v`  AS  select `ps`.`id` AS `id`,`ps`.`plant_id` AS `plant_id`,`ps`.`date` AS `date`,`ps`.`start_date` AS `start_date`,`ps`.`step_desc` AS `step_desc`,`ps`.`farmer_id` AS `farmer_id`,`ps`.`action` AS `action`,`pm`.`plant_name` AS `plant_name`,`pm`.`description` AS `description` from (`personal_schedule_tb` `ps` left join `plant_setup_mst_tb` `pm` on((`pm`.`id` = `ps`.`plant_id`))) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cure_tb`
--
ALTER TABLE `cure_tb`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `disease_tb`
--
ALTER TABLE `disease_tb`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `farmer_tb`
--
ALTER TABLE `farmer_tb`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `personal_schedule_tb`
--
ALTER TABLE `personal_schedule_tb`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_personal_schedule_tb_farmer_tb` (`farmer_id`),
  ADD KEY `plant_id` (`plant_id`);

--
-- Indexes for table `plant_setup_chd_tb`
--
ALTER TABLE `plant_setup_chd_tb`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_plant_setup_chd_tb_plant_setup_mst_tb` (`plant_id`);

--
-- Indexes for table `plant_setup_mst_tb`
--
ALTER TABLE `plant_setup_mst_tb`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cure_tb`
--
ALTER TABLE `cure_tb`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `disease_tb`
--
ALTER TABLE `disease_tb`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `farmer_tb`
--
ALTER TABLE `farmer_tb`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `personal_schedule_tb`
--
ALTER TABLE `personal_schedule_tb`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `plant_setup_chd_tb`
--
ALTER TABLE `plant_setup_chd_tb`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `plant_setup_mst_tb`
--
ALTER TABLE `plant_setup_mst_tb`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `personal_schedule_tb`
--
ALTER TABLE `personal_schedule_tb`
  ADD CONSTRAINT `FK_personal_schedule_tb_farmer_tb` FOREIGN KEY (`farmer_id`) REFERENCES `farmer_tb` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_personal_schedule_tb_plant_setup_mst_tb` FOREIGN KEY (`plant_id`) REFERENCES `plant_setup_mst_tb` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `plant_setup_chd_tb`
--
ALTER TABLE `plant_setup_chd_tb`
  ADD CONSTRAINT `FK_plant_setup_chd_tb_plant_setup_mst_tb` FOREIGN KEY (`plant_id`) REFERENCES `plant_setup_mst_tb` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
