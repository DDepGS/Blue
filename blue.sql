-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-07-2023 a las 03:44:17
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `blue`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentarios`
--

CREATE TABLE `comentarios` (
  `ID_comentario` int(11) NOT NULL,
  `Contenido` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ID_paciente` int(11) DEFAULT NULL,
  `ID_especialista` int(11) DEFAULT NULL,
  `Fecha` date NOT NULL,
  `ID_publicacion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `comentarios`
--

INSERT INTO `comentarios` (`ID_comentario`, `Contenido`, `ID_paciente`, `ID_especialista`, `Fecha`, `ID_publicacion`) VALUES
(18, 'Muy buena información', NULL, 22, '2022-11-27', 45);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `conversations`
--

CREATE TABLE `conversations` (
  `id` int(11) NOT NULL,
  `lastMessage` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `mailReceiver` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `receiverName` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `mailSender` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `senderName` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `conversations`
--

INSERT INTO `conversations` (`id`, `lastMessage`, `mailReceiver`, `receiverName`, `mailSender`, `senderName`, `date`) VALUES
(27, 'cXVlIG9uZGE=', 'claysentou@gmail.com', 'Francisco Albarran Quintanilla', 'claysentou@hotmail.com', 'Juan López Rodríguez', '2022-11-23 13:11:28'),
(28, 'aG9saQ==', 'mioygaru@gmail.com', 'Daniela Gutiérrez Sandoval', 'mioygaru@hotmail.com', 'Juan Villano Carranza', '2022-12-12 10:12:08');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_animo`
--

CREATE TABLE `estado_animo` (
  `ID_animo` int(11) NOT NULL,
  `Animo` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `ID_paciente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `estado_animo`
--

INSERT INTO `estado_animo` (`ID_animo`, `Animo`, `Fecha`, `ID_paciente`) VALUES
(60, 1, '2022-10-08', 12),
(63, 3, '2022-10-09', 12),
(64, 2, '2022-10-12', 12),
(65, 4, '2022-10-13', 12),
(66, 5, '2022-10-20', 12),
(68, 5, '2022-11-16', 19),
(69, 1, '2022-11-17', 19),
(70, 5, '2022-11-23', 19),
(71, 1, '2022-11-24', 19),
(73, 2, '2022-11-25', 21),
(74, 1, '2022-11-27', 21);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foro`
--

CREATE TABLE `foro` (
  `ID_publicacion` int(11) NOT NULL,
  `Titulo` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Contenido` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ID_paciente` int(11) DEFAULT NULL,
  `ID_especialista` int(11) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `anonimo` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `foro`
--

INSERT INTO `foro` (`ID_publicacion`, `Titulo`, `Contenido`, `ID_paciente`, `ID_especialista`, `Fecha`, `anonimo`) VALUES
(39, 'Como puedo mejorar mi condición fisica', 'Hola, soy paquito', 19, NULL, '2022-11-17', '0'),
(45, 'El chocolate negro ayuda', 'El chocolate negro ayuda con la depresión', 21, NULL, '2022-11-25', '0'),
(47, 'Otra publicación sobre el sueño', 'Uno debe dormir en promedio 8 horas', 21, NULL, '2022-11-27', '0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensaje`
--

CREATE TABLE `mensaje` (
  `id` int(11) NOT NULL,
  `mailSender` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `mailReceiver` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `mensaje` varchar(2000) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `mensaje`
--

INSERT INTO `mensaje` (`id`, `mailSender`, `mailReceiver`, `mensaje`, `fecha`) VALUES
(67, 'claysentou@hotmail.com', 'claysentou@gmail.com', 'SG9sYSEgQ29tbyBlc3TDoXM/', '2022-11-23 12:11:44'),
(68, 'claysentou@gmail.com', 'claysentou@hotmail.com', 'TWUgc2llbnRvIG11Y2hvIG1lam9yIQ==', '2022-11-23 12:11:34'),
(69, 'claysentou@gmail.com', 'claysentou@hotmail.com', 'aG9sYSBkZSBudWV2bw==', '2022-11-23 13:11:10'),
(70, 'claysentou@hotmail.com', 'claysentou@gmail.com', 'cXVlIG9uZGE=', '2022-11-23 13:11:28'),
(71, 'mioygaru@hotmail.com', 'mioygaru@gmail.com', 'SG9sYQ==', '2022-11-25 19:11:45'),
(72, 'mioygaru@gmail.com', 'mioygaru@hotmail.com', 'SG9sYSA=', '2022-11-25 20:11:36'),
(73, 'mioygaru@hotmail.com', 'mioygaru@gmail.com', 'Y8OzbW8gZXN0w6FzPw==', '2022-11-27 18:11:06'),
(74, 'mioygaru@gmail.com', 'mioygaru@hotmail.com', 'QmllbiB5IHTDuj8=', '2022-11-27 18:11:01'),
(75, 'mioygaru@hotmail.com', 'mioygaru@gmail.com', 'aG9saQ==', '2022-12-12 10:12:08');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificaciones`
--

CREATE TABLE `notificaciones` (
  `ID_notificacion` int(11) NOT NULL,
  `Nombre` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Descripcion` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Estado` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Pendiente',
  `ID_paciente` int(11) DEFAULT NULL,
  `ID_especialista` int(11) DEFAULT NULL,
  `Tipo` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `notificaciones`
--

INSERT INTO `notificaciones` (`ID_notificacion`, `Nombre`, `Descripcion`, `Estado`, `ID_paciente`, `ID_especialista`, `Tipo`) VALUES
(1, 'Prueba', 'Soy una notificacion de prueba', 'Visto', 12, NULL, 'Chat'),
(2, 'Prueba', 'Otra prueba', 'Visto', 12, NULL, 'Comentario'),
(3, 'Prueba', 'Soy una notificacion de prueba', 'Pendiente', NULL, 18, 'Chat'),
(4, 'Nuevo Comentario', 'Tienes un nuevo comentario en', 'Pendiente', 12, NULL, 'Comentario'),
(6, 'Nuevo Comentario', 'Tienes un nuevo comentario enTitulo', 'Pendiente', 13, NULL, 'Comentario'),
(7, 'Nuevo Comentario', 'Tienes un nuevo comentario en Titulo', 'Pendiente', 13, NULL, 'Comentario'),
(8, 'Nuevo Comentario', 'Tienes un nuevo comentario en Otra publicación', 'Visto', NULL, 18, 'Comentario'),
(12, 'Nuevo Comentario', 'Tienes un nuevo comentario en Prueba', 'Pendiente', NULL, 18, 'Comentario'),
(13, 'Nuevo Comentario', 'Tienes un nuevo comentario en Otra publicación', 'Pendiente', NULL, 18, 'Comentario'),
(17, 'Prueba', 'Subir fecha', 'Pendiente', 12, NULL, 'Recordatorio'),
(20, 'Prueba', 'Subir fecha', 'Pendiente', 12, NULL, 'Recordatorio'),
(21, 'Más pruebas', 'Para las fechas', 'Pendiente', 12, NULL, 'Recordatorio'),
(22, 'Prueba de especialista', 'Especialista', 'Pendiente', 12, NULL, 'Recordatorio'),
(23, 'Werwerwer', 'Werwerwe', 'Visto', 19, NULL, 'Recordatorio'),
(31, 'Nuevo Mensaje de: ', 'Juan Paco Pedro:mioygaru@gmail.com', 'Visto', NULL, 18, 'Chat'),
(32, 'Nuevo Mensaje de: ', 'Paco Juan De la Mar:correoedit@gmail.com', 'Visto', 12, NULL, 'Chat'),
(33, 'Nuevo Mensaje de: ', 'Juan Paco Pedro:mioygaru@gmail.com', 'Visto', NULL, 18, 'Chat'),
(34, 'Nuevo Comentario', 'Tienes un nuevo comentario en Como puedo mejorar mi condición fisica', 'Visto', 19, NULL, 'Comentario'),
(35, 'Nuevo Mensaje de: ', 'Juan Paco Pedro:mioygaru@gmail.com', 'Pendiente', NULL, 20, 'Chat'),
(36, 'Nuevo Mensaje de: ', 'Juan Paco Pedro:mioygaru@gmail.com', 'Pendiente', NULL, 18, 'Chat'),
(37, 'Nuevo Mensaje de: ', 'Juan Paco Pedro:mioygaru@gmail.com', 'Pendiente', NULL, 18, 'Chat'),
(38, 'Nuevo Mensaje de: ', 'Juan Paco Pedro:mioygaru@gmail.com', 'Pendiente', NULL, 18, 'Chat'),
(39, 'Nuevo Mensaje de: ', 'Juan Paco Pedro:mioygaru@gmail.com', 'Pendiente', NULL, 18, 'Chat'),
(40, 'Nuevo Mensaje de: ', 'Juan Paco Pedro:mioygaru@gmail.com', 'Visto', NULL, 18, 'Chat'),
(41, 'Nuevo Mensaje de: ', 'Juan Paco Pedro:mioygaru@gmail.com', 'Pendiente', NULL, 18, 'Chat'),
(42, 'Nuevo Mensaje de: ', 'Paco Juan De la Mar:correoedit@gmail.com', 'Visto', 12, NULL, 'Chat'),
(43, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 19, NULL, 'Chat'),
(44, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 12, NULL, 'Chat'),
(45, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 19, NULL, 'Chat'),
(46, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 19, NULL, 'Chat'),
(47, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 19, NULL, 'Chat'),
(48, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 19, NULL, 'Chat'),
(49, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 19, NULL, 'Chat'),
(50, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 19, NULL, 'Chat'),
(51, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 19, NULL, 'Chat'),
(52, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 19, NULL, 'Chat'),
(53, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 19, NULL, 'Chat'),
(54, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Pendiente', 12, NULL, 'Chat'),
(55, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Visto', 19, NULL, 'Chat'),
(56, 'Nuevo Mensaje de: ', 'Francisco Albarran Quintanilla:claysentou@gmail.com', 'Pendiente', NULL, 21, 'Chat'),
(57, 'Nuevo Mensaje de: ', 'Francisco Albarran Quintanilla:claysentou@gmail.com', 'Pendiente', NULL, 21, 'Chat'),
(58, 'Nuevo Mensaje de: ', 'Juan López Rodríguez:claysentou@hotmail.com', 'Visto', 19, NULL, 'Chat'),
(59, 'Tomar pastillas', 'Toma tus pastillas', 'Pendiente', 19, NULL, 'Recordatorio'),
(60, 'Nuevo Mensaje de: ', 'Juan Villano  Carranza :mioygaru@hotmail.com', 'Visto', 21, NULL, 'Chat'),
(61, 'Nuevo Comentario', 'Tienes un nuevo comentario en Como puedo mejorar mi condición fisica', 'Pendiente', 19, NULL, 'Comentario'),
(62, 'Nuevo Mensaje de: ', 'Daniela Gutiérrez Sandoval:mioygaru@gmail.com', 'Pendiente', NULL, 22, 'Chat'),
(63, 'Nuevo Comentario', 'Tienes un nuevo comentario en El chocolate negro ayuda', 'Visto', 21, NULL, 'Comentario'),
(64, 'Nuevo Mensaje de: ', 'Juan Villano Carranza:mioygaru@hotmail.com', 'Visto', 21, NULL, 'Chat'),
(65, 'Medicina', 'Toma tu medicina', 'Visto', 21, NULL, 'Recordatorio'),
(66, 'Nuevo Mensaje de: ', 'Daniela Gutiérrez Sandoval:mioygaru@gmail.com', 'Pendiente', NULL, 22, 'Chat'),
(67, 'Nuevo Mensaje de: ', 'Juan Villano Carranza:mioygaru@hotmail.com', 'Pendiente', 21, NULL, 'Chat');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recordatorios`
--

CREATE TABLE `recordatorios` (
  `ID_recordatorio` int(11) NOT NULL,
  `NombreRec` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Descripcion` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ID_paciente` int(11) DEFAULT NULL,
  `TiempoEj` time NOT NULL,
  `Repeticion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `recordatorios`
--

INSERT INTO `recordatorios` (`ID_recordatorio`, `NombreRec`, `Descripcion`, `ID_paciente`, `TiempoEj`, `Repeticion`) VALUES
(3, 'Prueba', 'Actualizacion 3', 12, '11:00:00', 24),
(4, 'Más pruebas', 'Para las fechas', 12, '00:08:00', 0),
(5, 'Prueba', 'Prueba de que funciona el regi', 12, '02:03:00', 0),
(8, 'Registro App', 'App', 12, '01:00:00', 0),
(9, 'Otra prueba', 'Prueba número 378', 12, '11:36:00', 24),
(10, 'Prueba', '176', 12, '14:15:00', 24),
(11, 'Prueba de especialista', 'Especialista', 12, '00:35:00', 1),
(13, 'Nombre', 'Rec', 12, '02:13:00', 0),
(14, 'RecPrueba1', 'En otro paciente', 13, '10:23:00', 24),
(17, 'Nuevo', 'Recordar', 12, '01:33:00', 0),
(25, 'Tomar pastillas', 'Toma tus pastillas', 19, '23:48:00', 1),
(29, 'Dormir', 'Hora de dormir', 21, '22:00:00', 24);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `re_paciente`
--

CREATE TABLE `re_paciente` (
  `ID_REpacientes` int(11) NOT NULL,
  `ID_especialista` int(11) NOT NULL,
  `ID_paciente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `re_paciente`
--

INSERT INTO `re_paciente` (`ID_REpacientes`, `ID_especialista`, `ID_paciente`) VALUES
(15, 18, 12),
(16, 18, 13),
(24, 20, 12),
(27, 21, 19),
(31, 22, 21);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `u_especialista`
--

CREATE TABLE `u_especialista` (
  `ID_especialista` int(11) NOT NULL,
  `Nombre` varchar(30) NOT NULL,
  `APaterno` varchar(20) NOT NULL,
  `AMaterno` varchar(20) DEFAULT NULL,
  `Profesion` varchar(30) NOT NULL,
  `Usuario` varchar(15) NOT NULL,
  `Correo` varchar(30) NOT NULL,
  `Contrasena` varchar(15) NOT NULL,
  `Telefono` varchar(15) NOT NULL,
  `Cedula` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `u_especialista`
--

INSERT INTO `u_especialista` (`ID_especialista`, `Nombre`, `APaterno`, `AMaterno`, `Profesion`, `Usuario`, `Correo`, `Contrasena`, `Telefono`, `Cedula`) VALUES
(18, 'Paco', 'Juan', 'De la Mar', 'Psiquiatra', 'Juanpp', 'correoedit@gmail.com', '8W1L7EEH', '495659464', 'jkad9821'),
(20, 'Julio Esteban Ricardo', 'de la Rosa', 'Montoya', 'Psiquiatra', 'simon', 'soyuncorrreo@gmail.com', 'contrasena2', '492334567', '3545451321'),
(21, 'Juan', 'López', 'Rodríguez', 'Psicologo', 'juanitoLR', 'claysentou@hotmail.com', 'contrasena', '2147483647', '81223344'),
(22, 'Juan', 'Villano', 'Carranza', 'Psicólogo', 'carranJ', 'mioygaru@hotmail.com', 'nuevacontra', '4923586595', '10928403');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `u_paciente`
--

CREATE TABLE `u_paciente` (
  `ID_paciente` int(11) NOT NULL,
  `Nombre` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `APaterno` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `AMaterno` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `Diagnostico` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Contrasena` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Medicamento` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `Correo` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Usuario` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Telefono` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `u_paciente`
--

INSERT INTO `u_paciente` (`ID_paciente`, `Nombre`, `APaterno`, `AMaterno`, `Diagnostico`, `Contrasena`, `Medicamento`, `Correo`, `Usuario`, `Telefono`) VALUES
(12, 'Juan', 'Paco', 'Pedro', 'Distimia', 'FJO9MNDA', 'Olanzapina', '', 'usuario', '462956454'),
(13, 'Juan', 'Paco', 'Pedro', 'Distimia', 'contrasena', 'Olanzapina', 'correo@gmail.com', 'usuario', '498272818'),
(14, 'Juan', 'Paco', 'Pedro', 'Distimia', 'contrasena', 'Olanzapina', 'correo@gmail.com', 'usuario', '498272818'),
(15, 'Juan', 'Paco', 'Pedro', 'Distimia', 'contrasena', 'Olanzapina', 'correo@gmail.com', 'usuario', '498272818'),
(16, 'Juan', 'Paco', 'Pedro', 'Distimia', 'contrasena', 'Olanzapina', 'correo@gmail.com', 'usuario', '498272818'),
(17, 'Juan', 'Paco', 'Pedro', 'Distimia', 'contrasena', 'Olanzapina', 'correo@gmail.com', 'usuario', '498272818'),
(18, 'Juan', 'Paco', 'Pedro', 'Distimia', 'contrasena', 'Olanzapina', 'correo@gmail.com', 'usuario', '498272818'),
(19, 'Francisco', 'Albarran', 'Quintanilla', 'Depresión crónica', 'amoakaren', 'Fluoxetina', 'claysentou@gmail.com', 'paquitoaq', '2147483647'),
(20, 'Asdasdasad', 'Hghghghg', 'Asdass', 'Sdadasdas', 'asdasdsadsaa', '', 'asdasds@gmail.com', 'adasdad', '2147483647'),
(21, 'Daniela', 'Gutiérrez', 'Sandoval', 'Depresión psicótica', '3GTQ6PXU', 'Venlafaxina', 'mioygaru@gmail.com', 'deepgs', '4922388076'),
(34, 'Daniel', 'Rodriguez', 'Martinez', 'Distimia', 'contra', 'Fluoxetina', 'uncorreo@gmail.com', 'danyrm', '4925658735');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comentarios`
--
ALTER TABLE `comentarios`
  ADD PRIMARY KEY (`ID_comentario`),
  ADD KEY `ID_pacienteC` (`ID_paciente`),
  ADD KEY `ID_especialistaC` (`ID_especialista`),
  ADD KEY `ID_publicacionC` (`ID_publicacion`);

--
-- Indices de la tabla `conversations`
--
ALTER TABLE `conversations`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `estado_animo`
--
ALTER TABLE `estado_animo`
  ADD PRIMARY KEY (`ID_animo`),
  ADD KEY `estado_animo_paciente` (`ID_paciente`);

--
-- Indices de la tabla `foro`
--
ALTER TABLE `foro`
  ADD PRIMARY KEY (`ID_publicacion`),
  ADD KEY `ID_pacienteP` (`ID_paciente`),
  ADD KEY `ID_especialistaP` (`ID_especialista`);

--
-- Indices de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD PRIMARY KEY (`ID_notificacion`),
  ADD KEY `ID_pacienteN` (`ID_paciente`),
  ADD KEY `ID_especialistN` (`ID_especialista`);

--
-- Indices de la tabla `recordatorios`
--
ALTER TABLE `recordatorios`
  ADD PRIMARY KEY (`ID_recordatorio`),
  ADD KEY `ID_pacienteRec` (`ID_paciente`);

--
-- Indices de la tabla `re_paciente`
--
ALTER TABLE `re_paciente`
  ADD PRIMARY KEY (`ID_REpacientes`),
  ADD KEY `ID_paciente` (`ID_paciente`),
  ADD KEY `ID_especialista` (`ID_especialista`);

--
-- Indices de la tabla `u_especialista`
--
ALTER TABLE `u_especialista`
  ADD PRIMARY KEY (`ID_especialista`);

--
-- Indices de la tabla `u_paciente`
--
ALTER TABLE `u_paciente`
  ADD PRIMARY KEY (`ID_paciente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comentarios`
--
ALTER TABLE `comentarios`
  MODIFY `ID_comentario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `conversations`
--
ALTER TABLE `conversations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT de la tabla `estado_animo`
--
ALTER TABLE `estado_animo`
  MODIFY `ID_animo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;

--
-- AUTO_INCREMENT de la tabla `foro`
--
ALTER TABLE `foro`
  MODIFY `ID_publicacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- AUTO_INCREMENT de la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  MODIFY `ID_notificacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT de la tabla `recordatorios`
--
ALTER TABLE `recordatorios`
  MODIFY `ID_recordatorio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `re_paciente`
--
ALTER TABLE `re_paciente`
  MODIFY `ID_REpacientes` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `u_especialista`
--
ALTER TABLE `u_especialista`
  MODIFY `ID_especialista` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `u_paciente`
--
ALTER TABLE `u_paciente`
  MODIFY `ID_paciente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comentarios`
--
ALTER TABLE `comentarios`
  ADD CONSTRAINT `ID_especialistaC` FOREIGN KEY (`ID_especialista`) REFERENCES `u_especialista` (`ID_especialista`),
  ADD CONSTRAINT `ID_pacienteC` FOREIGN KEY (`ID_paciente`) REFERENCES `u_paciente` (`ID_paciente`),
  ADD CONSTRAINT `ID_publicacionC` FOREIGN KEY (`ID_publicacion`) REFERENCES `foro` (`ID_publicacion`);

--
-- Filtros para la tabla `estado_animo`
--
ALTER TABLE `estado_animo`
  ADD CONSTRAINT `estado_animo_paciente` FOREIGN KEY (`ID_paciente`) REFERENCES `u_paciente` (`ID_paciente`);

--
-- Filtros para la tabla `foro`
--
ALTER TABLE `foro`
  ADD CONSTRAINT `ID_especialistaP` FOREIGN KEY (`ID_especialista`) REFERENCES `u_especialista` (`ID_especialista`),
  ADD CONSTRAINT `ID_pacienteP` FOREIGN KEY (`ID_paciente`) REFERENCES `u_paciente` (`ID_paciente`);

--
-- Filtros para la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD CONSTRAINT `ID_especialistN` FOREIGN KEY (`ID_especialista`) REFERENCES `u_especialista` (`ID_especialista`),
  ADD CONSTRAINT `ID_pacienteN` FOREIGN KEY (`ID_paciente`) REFERENCES `u_paciente` (`ID_paciente`);

--
-- Filtros para la tabla `recordatorios`
--
ALTER TABLE `recordatorios`
  ADD CONSTRAINT `ID_pacienteRec` FOREIGN KEY (`ID_paciente`) REFERENCES `u_paciente` (`ID_paciente`);

--
-- Filtros para la tabla `re_paciente`
--
ALTER TABLE `re_paciente`
  ADD CONSTRAINT `ID_especialista` FOREIGN KEY (`ID_especialista`) REFERENCES `u_especialista` (`ID_especialista`),
  ADD CONSTRAINT `ID_paciente` FOREIGN KEY (`ID_paciente`) REFERENCES `u_paciente` (`ID_paciente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
