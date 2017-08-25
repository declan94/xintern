-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2017-04-27 12:49:42
-- 服务器版本： 5.7.12
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eyewater`
--

-- --------------------------------------------------------

--
-- 表的结构 `ew_admin`
--

CREATE TABLE `ew_admin` (
  `a_id` int(10) UNSIGNED NOT NULL,
  `a_username` varchar(128) NOT NULL,
  `a_password` varchar(128) NOT NULL,
  `a_password2` varchar(128) NOT NULL,
  `a_name` varchar(128) NOT NULL,
  `a_tel` varchar(20) NOT NULL,
  `a_role` smallint(6) NOT NULL,
  `a_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_admin`
--

INSERT INTO `ew_admin` (`a_id`, `a_username`, `a_password`, `a_password2`, `a_name`, `a_tel`, `a_role`, `a_createtime`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '21232f297a57a5a743894a0e4a801fc3', 'Admin', '', 0, '2017-01-13 07:46:06'),
(2, 'finance', '098f6bcd4621d373cade4e832627b4f6', '098f6bcd4621d373cade4e832627b4f6', 'finance', '18810302015', 1, '2017-01-17 06:39:29');

-- --------------------------------------------------------

--
-- 表的结构 `ew_bonus`
--

CREATE TABLE `ew_bonus` (
  `b_id` bigint(20) UNSIGNED NOT NULL,
  `b_memberid` bigint(20) UNSIGNED NOT NULL,
  `b_orderid` bigint(20) UNSIGNED NOT NULL,
  `b_paid123` tinyint(1) NOT NULL DEFAULT '0' COMMENT '123奖项是否已兑现',
  `b_paytime123` datetime DEFAULT NULL,
  `b_paid4` tinyint(1) NOT NULL DEFAULT '0' COMMENT '废弃',
  `b_paytime4` datetime DEFAULT NULL COMMENT '废弃',
  `b_value1` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '推广奖金',
  `b_value2` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '团队奖金',
  `b_value3` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '代奖',
  `b_value4` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '平级奖',
  `b_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_bonus`
--

INSERT INTO `ew_bonus` (`b_id`, `b_memberid`, `b_orderid`, `b_paid123`, `b_paytime123`, `b_paid4`, `b_paytime4`, `b_value1`, `b_value2`, `b_value3`, `b_value4`, `b_createtime`) VALUES
(1, 1, 2, 1, '2017-04-26 11:43:00', 0, NULL, '19.90', '19.90', '9.95', '0.00', '2017-04-26 03:19:23');

-- --------------------------------------------------------

--
-- 表的结构 `ew_config`
--

CREATE TABLE `ew_config` (
  `c_level` int(11) NOT NULL,
  `c_reward1` double NOT NULL COMMENT '推广奖金比例（代理奖金）',
  `c_reward2` double NOT NULL COMMENT '团队奖金比例（绩效奖金）',
  `c_reward3` varchar(255) NOT NULL COMMENT '代奖比例',
  `c_reward4` double NOT NULL COMMENT '平级奖比例',
  `c_promote_cnt` int(10) UNSIGNED NOT NULL COMMENT '直推人数',
  `c_achieve_large` decimal(12,2) UNSIGNED NOT NULL,
  `c_achieve_small` decimal(12,2) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_config`
--

INSERT INTO `ew_config` (`c_level`, `c_reward1`, `c_reward2`, `c_reward3`, `c_reward4`, `c_promote_cnt`, `c_achieve_large`, `c_achieve_small`) VALUES
(1, 0.1, 0.1, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 0, '0.00', '0.00'),
(2, 0.1, 0.11, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 2, '5000.00', '2000.00'),
(3, 0.1, 0.12, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 3, '10000.00', '4000.00'),
(4, 0.1, 0.13, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 5, '20000.00', '8000.00'),
(5, 0.1, 0.14, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 10, '5000000.00', '1500000.00'),
(6, 0.1, 0.15, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 20, '100000.00', '40000.00'),
(7, 0.1, 0.16, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 40, '200000.00', '80000.00'),
(8, 0.1, 0.17, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 80, '500000.00', '200000.00'),
(9, 0.1, 0.18, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 160, '1000000.00', '400000.00'),
(10, 0.1, 0.19, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 320, '5000000.00', '2000000.00'),
(11, 0.1, 0.2, '[0.05,0.07,0.1]/java.util.ArrayList', 0, 640, '50000000.00', '20000000.00');

-- --------------------------------------------------------

--
-- 表的结构 `ew_deliverylog`
--

CREATE TABLE `ew_deliverylog` (
  `d_id` bigint(20) UNSIGNED NOT NULL,
  `d_orderid` bigint(20) UNSIGNED NOT NULL,
  `d_opid` bigint(20) UNSIGNED DEFAULT NULL,
  `d_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='总部发货清单表';

-- --------------------------------------------------------

--
-- 表的结构 `ew_member`
--

CREATE TABLE `ew_member` (
  `m_id` bigint(20) UNSIGNED NOT NULL,
  `m_superiorid` bigint(20) UNSIGNED NOT NULL COMMENT '推荐人（上级）id',
  `m_parentid` bigint(20) UNSIGNED NOT NULL COMMENT '双规分区的父节点id',
  `m_leftid` bigint(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '左区根节点id',
  `m_rightid` bigint(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '右区根节点id',
  `m_achieve` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '业绩，自己、自己左区及右区的总消费。所以leftid对应的m_achieve即为自己的左区业绩',
  `m_score` decimal(12,2) NOT NULL DEFAULT '0.00',
  `m_score0` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '积分中包含的充值积分部分（不可提现）',
  `m_score2pay` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '未到账奖金',
  `m_rscore` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '责消积分',
  `m_rscore2pay` decimal(12,2) NOT NULL DEFAULT '0.00',
  `m_teamsize` int(10) UNSIGNED NOT NULL DEFAULT '1',
  `m_username` varchar(255) NOT NULL,
  `m_password` varchar(128) NOT NULL,
  `m_paypwd` varchar(128) NOT NULL,
  `m_name` varchar(255) NOT NULL,
  `m_tel` varchar(255) NOT NULL,
  `m_gender` tinyint(3) UNSIGNED NOT NULL DEFAULT '0',
  `m_idno` varchar(20) NOT NULL,
  `m_level` smallint(5) UNSIGNED NOT NULL DEFAULT '0',
  `m_area` varchar(20) NOT NULL,
  `m_reward1` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00',
  `m_reward2` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00',
  `m_reward3` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00',
  `m_reward4` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00',
  `m_shopid` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `m_frozen` tinyint(1) NOT NULL DEFAULT '0',
  `m_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_member`
--

INSERT INTO `ew_member` (`m_id`, `m_superiorid`, `m_parentid`, `m_leftid`, `m_rightid`, `m_achieve`, `m_score`, `m_score0`, `m_score2pay`, `m_rscore`, `m_rscore2pay`, `m_teamsize`, `m_username`, `m_password`, `m_paypwd`, `m_name`, `m_tel`, `m_gender`, `m_idno`, `m_level`, `m_area`, `m_reward1`, `m_reward2`, `m_reward3`, `m_reward4`, `m_shopid`, `m_frozen`, `m_createtime`) VALUES
(1, 0, 0, 2, 3, '697.00', '46.77', '2.00', '0.00', '4.97', '0.00', 3, 'user', '098f6bcd4621d373cade4e832627b4f6', '098f6bcd4621d373cade4e832627b4f6', 'aaa', '3423123', 1, '12', 1, '北京市', '19.90', '19.90', '9.95', '0.00', 1, 0, '2017-04-26 03:16:54'),
(2, 1, 1, 0, 0, '199.00', '1.00', '0.00', '0.00', '0.00', '0.00', 1, 'user1', '098f6bcd4621d373cade4e832627b4f6', '098f6bcd4621d373cade4e832627b4f6', 'bbb', '131531', 2, '234124', 1, '北京市', '0.00', '0.00', '0.00', '0.00', 0, 0, '2017-04-26 03:18:33'),
(3, 1, 1, 0, 0, '0.00', '300.00', '300.00', '0.00', '0.00', '0.00', 1, 'user2', '098f6bcd4621d373cade4e832627b4f6', '098f6bcd4621d373cade4e832627b4f6', 'ccc', '1431241234', 0, '23414', 0, '北京市', '0.00', '0.00', '0.00', '0.00', 0, 0, '2017-04-26 11:07:18');

-- --------------------------------------------------------

--
-- 表的结构 `ew_news`
--

CREATE TABLE `ew_news` (
  `n_id` bigint(20) UNSIGNED NOT NULL,
  `n_title` varchar(255) NOT NULL,
  `n_content` longtext NOT NULL,
  `n_priority` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '置顶',
  `n_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ew_order`
--

CREATE TABLE `ew_order` (
  `o_id` bigint(20) UNSIGNED NOT NULL,
  `o_memberid` bigint(20) UNSIGNED NOT NULL,
  `o_shopid` bigint(20) UNSIGNED NOT NULL,
  `o_productid` bigint(20) UNSIGNED NOT NULL,
  `o_product_module` tinyint(3) UNSIGNED NOT NULL,
  `o_product_name` varchar(255) NOT NULL,
  `o_count` int(10) UNSIGNED NOT NULL,
  `o_sum` decimal(12,2) UNSIGNED NOT NULL,
  `o_receiver` varchar(255) NOT NULL,
  `o_tel` varchar(20) NOT NULL,
  `o_addr` varchar(255) NOT NULL,
  `o_delivery` varchar(255) NOT NULL DEFAULT '',
  `o_delivery_no` varchar(255) NOT NULL DEFAULT '',
  `o_status` smallint(5) UNSIGNED NOT NULL DEFAULT '0',
  `o_packaged` tinyint(1) NOT NULL DEFAULT '0',
  `o_bonus_generated` tinyint(1) NOT NULL DEFAULT '0',
  `o_achieve_counted` tinyint(1) NOT NULL DEFAULT '0',
  `o_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_order`
--

INSERT INTO `ew_order` (`o_id`, `o_memberid`, `o_shopid`, `o_productid`, `o_product_module`, `o_product_name`, `o_count`, `o_sum`, `o_receiver`, `o_tel`, `o_addr`, `o_delivery`, `o_delivery_no`, `o_status`, `o_packaged`, `o_bonus_generated`, `o_achieve_counted`, `o_createtime`) VALUES
(1, 1, 0, 1, 0, 'test', 1, '199.00', 'a', '23411', 'a', '', '', 0, 0, 1, 1, '2017-04-26 03:17:59'),
(2, 2, 0, 1, 0, 'test', 1, '199.00', 'vvv', '341', 'adfasdf', '', '', 0, 0, 1, 1, '2017-04-26 03:19:23'),
(3, 1, 1, 2, 1, 'b', 1, '299.00', 'a', '23411', 'a', '', '', 0, 0, 1, 1, '2017-04-27 08:33:28');

-- --------------------------------------------------------

--
-- 表的结构 `ew_order_package`
--

CREATE TABLE `ew_order_package` (
  `op_id` bigint(20) UNSIGNED NOT NULL,
  `op_shopid` bigint(20) UNSIGNED NOT NULL,
  `op_orderids` varchar(1000) NOT NULL,
  `op_products` varchar(1000) NOT NULL,
  `op_delivery` varchar(255) NOT NULL DEFAULT '',
  `op_delivery_no` varchar(255) NOT NULL DEFAULT '',
  `op_sent` tinyint(1) NOT NULL DEFAULT '0',
  `op_sent_time` datetime DEFAULT NULL,
  `op_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ew_product`
--

CREATE TABLE `ew_product` (
  `p_id` bigint(20) UNSIGNED NOT NULL,
  `p_name` varchar(255) NOT NULL,
  `p_module` tinyint(3) UNSIGNED NOT NULL,
  `p_price` decimal(12,2) UNSIGNED NOT NULL,
  `p_imgurl` varchar(255) NOT NULL,
  `p_desc` text NOT NULL,
  `p_detail` longtext NOT NULL,
  `p_status` smallint(5) UNSIGNED NOT NULL,
  `p_updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_product`
--

INSERT INTO `ew_product` (`p_id`, `p_name`, `p_module`, `p_price`, `p_imgurl`, `p_desc`, `p_detail`, `p_status`, `p_updatetime`) VALUES
(1, 'test', 0, '199.00', '/upload/2017-04-14_18-11-01_2b660.jpg', 'aaa', '<p>adsfa</p>', 0, '2017-04-14 10:11:05'),
(2, 'b', 1, '299.00', '/upload/2017-04-14_18-13-44_a9f55.jpeg', 'asdf', '<p>sadf</p>', 0, '2017-04-14 10:13:46');

-- --------------------------------------------------------

--
-- 表的结构 `ew_receiver`
--

CREATE TABLE `ew_receiver` (
  `r_id` bigint(20) UNSIGNED NOT NULL,
  `r_memberid` bigint(20) UNSIGNED NOT NULL,
  `r_name` varchar(20) NOT NULL,
  `r_tel` varchar(20) NOT NULL,
  `r_addr` varchar(255) NOT NULL,
  `r_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_receiver`
--

INSERT INTO `ew_receiver` (`r_id`, `r_memberid`, `r_name`, `r_tel`, `r_addr`, `r_createtime`) VALUES
(1, 1, 'a', '23411', 'a', '2017-04-26 03:17:48'),
(2, 2, 'vvv', '341', 'adfasdf', '2017-04-26 03:19:14');

-- --------------------------------------------------------

--
-- 表的结构 `ew_recharge`
--

CREATE TABLE `ew_recharge` (
  `r_id` bigint(20) UNSIGNED NOT NULL,
  `r_memberid` bigint(20) UNSIGNED NOT NULL,
  `r_srcid` bigint(20) UNSIGNED NOT NULL,
  `r_value` decimal(12,2) UNSIGNED NOT NULL,
  `r_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `r_canceled` tinyint(1) NOT NULL DEFAULT '0',
  `r_cancel_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_recharge`
--

INSERT INTO `ew_recharge` (`r_id`, `r_memberid`, `r_srcid`, `r_value`, `r_createtime`, `r_canceled`, `r_cancel_time`) VALUES
(1, 1, 0, '1000.00', '2017-04-26 03:17:17', 0, NULL),
(2, 2, 1, '200.00', '2017-04-26 03:19:00', 0, NULL),
(3, 3, 1, '200.00', '2017-04-26 11:07:34', 0, NULL),
(4, 3, 1, '100.00', '2017-04-26 11:10:09', 0, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `ew_rscorelog`
--

CREATE TABLE `ew_rscorelog` (
  `sl_id` bigint(20) UNSIGNED NOT NULL,
  `sl_memberid` bigint(20) UNSIGNED NOT NULL,
  `sl_type` tinyint(3) UNSIGNED NOT NULL,
  `sl_value` decimal(12,2) NOT NULL,
  `sl_srcid` bigint(20) UNSIGNED NOT NULL,
  `sl_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_rscorelog`
--

INSERT INTO `ew_rscorelog` (`sl_id`, `sl_memberid`, `sl_type`, `sl_value`, `sl_srcid`, `sl_createtime`) VALUES
(1, 1, 1, '4.97', 1, '2017-04-26 03:43:00');

-- --------------------------------------------------------

--
-- 表的结构 `ew_scorelog`
--

CREATE TABLE `ew_scorelog` (
  `sl_id` bigint(20) UNSIGNED NOT NULL,
  `sl_memberid` bigint(20) UNSIGNED NOT NULL,
  `sl_type` tinyint(3) UNSIGNED NOT NULL,
  `sl_value` decimal(12,2) NOT NULL,
  `sl_srcid` bigint(20) UNSIGNED NOT NULL,
  `sl_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_scorelog`
--

INSERT INTO `ew_scorelog` (`sl_id`, `sl_memberid`, `sl_type`, `sl_value`, `sl_srcid`, `sl_createtime`) VALUES
(1, 1, 4, '1000.00', 1, '2017-04-26 03:17:17'),
(2, 1, 0, '-199.00', 1, '2017-04-26 03:17:59'),
(3, 2, 4, '200.00', 2, '2017-04-26 03:19:00'),
(4, 1, 5, '-200.00', 2, '2017-04-26 03:19:00'),
(5, 2, 0, '-199.00', 2, '2017-04-26 03:19:23'),
(6, 1, 1, '44.77', 1, '2017-04-26 03:43:00'),
(7, 3, 4, '200.00', 3, '2017-04-26 11:07:34'),
(8, 1, 5, '-200.00', 3, '2017-04-26 11:07:34'),
(9, 3, 4, '100.00', 4, '2017-04-26 11:10:09'),
(10, 1, 5, '-100.00', 4, '2017-04-26 11:10:09'),
(11, 1, 0, '-299.00', 3, '2017-04-27 08:33:28');

-- --------------------------------------------------------

--
-- 表的结构 `ew_shop`
--

CREATE TABLE `ew_shop` (
  `s_id` bigint(20) UNSIGNED NOT NULL,
  `s_memberId` bigint(20) UNSIGNED NOT NULL,
  `s_name` varchar(255) NOT NULL,
  `s_addr` varchar(255) NOT NULL,
  `s_physical` tinyint(1) NOT NULL COMMENT '是否有实体店',
  `s_reward` decimal(12,2) NOT NULL DEFAULT '0.00',
  `s_status` smallint(5) UNSIGNED NOT NULL,
  `s_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `ew_shop`
--

INSERT INTO `ew_shop` (`s_id`, `s_memberId`, `s_name`, `s_addr`, `s_physical`, `s_reward`, `s_status`, `s_createtime`) VALUES
(1, 1, 'test shop', 'abcdefg', 1, '0.00', 1, '2017-04-27 08:23:26');

-- --------------------------------------------------------

--
-- 表的结构 `ew_shopbonus`
--

CREATE TABLE `ew_shopbonus` (
  `b_id` bigint(20) UNSIGNED NOT NULL,
  `b_shopid` bigint(20) UNSIGNED NOT NULL,
  `b_orderid` bigint(20) UNSIGNED NOT NULL,
  `b_paid` tinyint(1) NOT NULL DEFAULT '0',
  `b_paytime` datetime DEFAULT NULL,
  `b_value` decimal(12,2) UNSIGNED NOT NULL DEFAULT '0.00',
  `b_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ew_withdraw`
--

CREATE TABLE `ew_withdraw` (
  `w_id` bigint(20) UNSIGNED NOT NULL,
  `w_memberid` bigint(20) UNSIGNED NOT NULL,
  `w_value` decimal(12,2) UNSIGNED NOT NULL,
  `w_fee` decimal(12,2) UNSIGNED NOT NULL,
  `w_pay` decimal(12,2) NOT NULL,
  `w_paid` tinyint(1) NOT NULL DEFAULT '0',
  `w_paytime` datetime DEFAULT NULL,
  `w_receiver` varchar(255) NOT NULL,
  `w_bank` varchar(255) NOT NULL,
  `w_account` varchar(255) NOT NULL,
  `w_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ew_admin`
--
ALTER TABLE `ew_admin`
  ADD PRIMARY KEY (`a_id`),
  ADD UNIQUE KEY `a_username` (`a_username`);

--
-- Indexes for table `ew_bonus`
--
ALTER TABLE `ew_bonus`
  ADD PRIMARY KEY (`b_id`),
  ADD KEY `b_memberid` (`b_memberid`),
  ADD KEY `b_orderid` (`b_orderid`);

--
-- Indexes for table `ew_config`
--
ALTER TABLE `ew_config`
  ADD PRIMARY KEY (`c_level`);

--
-- Indexes for table `ew_deliverylog`
--
ALTER TABLE `ew_deliverylog`
  ADD PRIMARY KEY (`d_id`),
  ADD KEY `d_orderid` (`d_orderid`),
  ADD KEY `d_opid` (`d_opid`);

--
-- Indexes for table `ew_member`
--
ALTER TABLE `ew_member`
  ADD PRIMARY KEY (`m_id`),
  ADD UNIQUE KEY `m_username` (`m_username`),
  ADD KEY `m_superiorid` (`m_superiorid`),
  ADD KEY `m_parentid` (`m_parentid`),
  ADD KEY `m_leftid` (`m_leftid`),
  ADD KEY `m_rightid` (`m_rightid`);

--
-- Indexes for table `ew_news`
--
ALTER TABLE `ew_news`
  ADD PRIMARY KEY (`n_id`);

--
-- Indexes for table `ew_order`
--
ALTER TABLE `ew_order`
  ADD PRIMARY KEY (`o_id`),
  ADD KEY `o_memberid` (`o_memberid`),
  ADD KEY `o_productid` (`o_productid`);

--
-- Indexes for table `ew_order_package`
--
ALTER TABLE `ew_order_package`
  ADD PRIMARY KEY (`op_id`),
  ADD KEY `op_shopid` (`op_shopid`);

--
-- Indexes for table `ew_product`
--
ALTER TABLE `ew_product`
  ADD PRIMARY KEY (`p_id`);

--
-- Indexes for table `ew_receiver`
--
ALTER TABLE `ew_receiver`
  ADD PRIMARY KEY (`r_id`),
  ADD KEY `r_memberid` (`r_memberid`);

--
-- Indexes for table `ew_recharge`
--
ALTER TABLE `ew_recharge`
  ADD PRIMARY KEY (`r_id`),
  ADD KEY `r_memberid` (`r_memberid`),
  ADD KEY `r_srcid` (`r_srcid`);

--
-- Indexes for table `ew_rscorelog`
--
ALTER TABLE `ew_rscorelog`
  ADD PRIMARY KEY (`sl_id`),
  ADD KEY `sl_memberid` (`sl_memberid`);

--
-- Indexes for table `ew_scorelog`
--
ALTER TABLE `ew_scorelog`
  ADD PRIMARY KEY (`sl_id`),
  ADD KEY `sl_memberid` (`sl_memberid`);

--
-- Indexes for table `ew_shop`
--
ALTER TABLE `ew_shop`
  ADD PRIMARY KEY (`s_id`);

--
-- Indexes for table `ew_shopbonus`
--
ALTER TABLE `ew_shopbonus`
  ADD PRIMARY KEY (`b_id`),
  ADD KEY `b_memberid` (`b_shopid`),
  ADD KEY `b_orderid` (`b_orderid`);

--
-- Indexes for table `ew_withdraw`
--
ALTER TABLE `ew_withdraw`
  ADD PRIMARY KEY (`w_id`),
  ADD KEY `w_memberid` (`w_memberid`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `ew_admin`
--
ALTER TABLE `ew_admin`
  MODIFY `a_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- 使用表AUTO_INCREMENT `ew_bonus`
--
ALTER TABLE `ew_bonus`
  MODIFY `b_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- 使用表AUTO_INCREMENT `ew_deliverylog`
--
ALTER TABLE `ew_deliverylog`
  MODIFY `d_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `ew_member`
--
ALTER TABLE `ew_member`
  MODIFY `m_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- 使用表AUTO_INCREMENT `ew_news`
--
ALTER TABLE `ew_news`
  MODIFY `n_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `ew_order`
--
ALTER TABLE `ew_order`
  MODIFY `o_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- 使用表AUTO_INCREMENT `ew_order_package`
--
ALTER TABLE `ew_order_package`
  MODIFY `op_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `ew_product`
--
ALTER TABLE `ew_product`
  MODIFY `p_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- 使用表AUTO_INCREMENT `ew_receiver`
--
ALTER TABLE `ew_receiver`
  MODIFY `r_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- 使用表AUTO_INCREMENT `ew_recharge`
--
ALTER TABLE `ew_recharge`
  MODIFY `r_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- 使用表AUTO_INCREMENT `ew_rscorelog`
--
ALTER TABLE `ew_rscorelog`
  MODIFY `sl_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- 使用表AUTO_INCREMENT `ew_scorelog`
--
ALTER TABLE `ew_scorelog`
  MODIFY `sl_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- 使用表AUTO_INCREMENT `ew_shop`
--
ALTER TABLE `ew_shop`
  MODIFY `s_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- 使用表AUTO_INCREMENT `ew_shopbonus`
--
ALTER TABLE `ew_shopbonus`
  MODIFY `b_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `ew_withdraw`
--
ALTER TABLE `ew_withdraw`
  MODIFY `w_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
