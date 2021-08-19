-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 19-Ago-2021 às 19:56
-- Versão do servidor: 10.4.20-MariaDB
-- versão do PHP: 8.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `db_dp`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `acesso_user`
--

CREATE TABLE `acesso_user` (
  `cod` int(11) NOT NULL,
  `tbtelas_codTela` varchar(155) NOT NULL,
  `tbusuarios_codUsuario` int(11) NOT NULL,
  `permite_acesso` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `alteracao_rev`
--

CREATE TABLE `alteracao_rev` (
  `cod` int(11) NOT NULL,
  `item_cod` int(11) NOT NULL,
  `tbempresas_codEmp` int(11) NOT NULL,
  `rev_interna_a` int(11) NOT NULL,
  `data_rev_interna_a` date DEFAULT NULL,
  `rev_cliente_a` int(11) NOT NULL,
  `data_rev_cliente_a` date DEFAULT NULL,
  `tipo_revisao` int(11) NOT NULL,
  `possui_extencao` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `arquivos_alteracao`
--

CREATE TABLE `arquivos_alteracao` (
  `cod` int(11) NOT NULL,
  `tipo_arquivo_cod` int(11) NOT NULL,
  `link` varchar(155) NOT NULL,
  `alteracao_rev_cod` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `cop_nfe_i`
--

CREATE TABLE `cop_nfe_i` (
  `numero_nfe` int(11) NOT NULL,
  `dataImporta` date NOT NULL,
  `dataVencimento` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `ferramenta`
--

CREATE TABLE `ferramenta` (
  `cod_fer` int(11) NOT NULL,
  `cod_ferramenta` varchar(155) NOT NULL,
  `grupo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `item`
--

CREATE TABLE `item` (
  `cod` int(11) NOT NULL,
  `cod_item` varchar(255) NOT NULL,
  `descricao_item` varchar(155) NOT NULL,
  `tbempresas_codEmp` int(11) NOT NULL,
  `tipo_cont_rev` int(11) NOT NULL,
  `revisao_interna` int(11) NOT NULL,
  `data_rev_interna` date NOT NULL,
  `revisao_cliente` varchar(11) NOT NULL,
  `data_rev_cliente` date NOT NULL,
  `responsavel` varchar(55) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `maquina`
--

CREATE TABLE `maquina` (
  `cod_maq` int(11) NOT NULL,
  `codigo_maquina` varchar(45) NOT NULL,
  `descricao` varchar(155) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `nfe_diverge`
--

CREATE TABLE `nfe_diverge` (
  `cod_dv` int(11) NOT NULL,
  `fk_nfe_entrada_cod_nfe` int(11) NOT NULL,
  `tipoDiverge` varchar(45) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `data` date NOT NULL,
  `hora` varchar(8) NOT NULL,
  `dataAjuste` date NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `nfe_entrada`
--

CREATE TABLE `nfe_entrada` (
  `cod_nfe` int(11) NOT NULL,
  `num_nfe` int(11) NOT NULL,
  `nomeForne` varchar(155) NOT NULL,
  `link_nfe` varchar(255) NOT NULL,
  `obs_compra` varchar(155) DEFAULT NULL,
  `dataEmi` date NOT NULL,
  `dataInclu` date NOT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `ocorrencia_producao`
--

CREATE TABLE `ocorrencia_producao` (
  `cod_bo` int(11) NOT NULL,
  `ferramenta_cod_fer` int(11) NOT NULL,
  `valor_ultima_compra` double NOT NULL,
  `maquina_cod_maq` int(11) NOT NULL,
  `tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `motivo_quebra` varchar(255) NOT NULL,
  `item_cod` int(11) NOT NULL,
  `data` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `solicita_alteracao`
--

CREATE TABLE `solicita_alteracao` (
  `cod` int(11) NOT NULL,
  `alteracao_rev_cod` int(11) NOT NULL,
  `titulo` varchar(155) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `usuario_criador` varchar(45) NOT NULL,
  `data_criacao` date NOT NULL,
  `usuario_retorno` varchar(255) DEFAULT NULL,
  `data_retorno` date DEFAULT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbavalia`
--

CREATE TABLE `tbavalia` (
  `cod` int(11) NOT NULL,
  `tbempresas_codEmp` int(11) NOT NULL,
  `condPgmt` varchar(3) NOT NULL,
  `condEntrega` varchar(3) NOT NULL,
  `qualPreco` varchar(3) NOT NULL,
  `qualidade` varchar(3) NOT NULL,
  `avaliaTotal` varchar(3) NOT NULL,
  `dataAvalia` date NOT NULL,
  `statusAvalia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcontacesso`
--

CREATE TABLE `tbcontacesso` (
  `codAcesso` int(11) NOT NULL,
  `nomeEmp` varchar(155) NOT NULL,
  `veiculo` varchar(45) NOT NULL,
  `placa` varchar(8) NOT NULL,
  `nomePessoa` varchar(155) NOT NULL,
  `rg` varbinary(255) NOT NULL,
  `observa` varchar(255) NOT NULL,
  `horaEntrada` varchar(5) NOT NULL,
  `horaSaida` varchar(5) DEFAULT NULL,
  `statusAcesso` int(11) NOT NULL,
  `dataAcesso` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcontrato`
--

CREATE TABLE `tbcontrato` (
  `codContra` int(10) UNSIGNED NOT NULL,
  `tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `dataAdmiContra` date NOT NULL,
  `responsaContra` varchar(45) DEFAULT NULL,
  `observaContra` varchar(155) DEFAULT NULL,
  `dataVenc30dd` date NOT NULL,
  `dataVenc90dd` date NOT NULL,
  `statusContra` int(11) NOT NULL,
  `statusContra30dd` int(11) NOT NULL,
  `statusContra90dd` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcontsaida`
--

CREATE TABLE `tbcontsaida` (
  `codSaida` int(10) UNSIGNED NOT NULL,
  `tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `dataSaidaFuncio` date NOT NULL,
  `horaSaidaFuncio` varchar(5) NOT NULL,
  `retornaTrabalho` varchar(4) NOT NULL,
  `motivoSaida` int(45) NOT NULL,
  `espLocal` varchar(255) DEFAULT NULL,
  `responsavel` varchar(45) DEFAULT NULL,
  `statusSaida` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcoparti`
--

CREATE TABLE `tbcoparti` (
  `codCop` int(11) NOT NULL,
  `tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `geradoPar` int(2) NOT NULL,
  `tbdependentes_codDependente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcotista`
--

CREATE TABLE `tbcotista` (
  `codCota` int(11) NOT NULL,
  `tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `dataAdmi` date DEFAULT NULL,
  `dataVenci` date DEFAULT NULL,
  `observa` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbdependentes`
--

CREATE TABLE `tbdependentes` (
  `codDependente` int(11) NOT NULL,
  `nomeDependente` varchar(155) NOT NULL,
  `tipoDependente` varchar(155) NOT NULL,
  `tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `cpfDependente` varbinary(255) NOT NULL,
  `statusDependente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbempresas`
--

CREATE TABLE `tbempresas` (
  `codEmp` int(11) NOT NULL,
  `nomeEmp` varchar(255) NOT NULL,
  `mailEmp` varchar(155) NOT NULL,
  `tipoEmp` varchar(155) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbeventos`
--

CREATE TABLE `tbeventos` (
  `codEvento` int(11) NOT NULL,
  `descriEvento` varchar(255) NOT NULL,
  `tipoEvento` varchar(255) NOT NULL,
  `tipoProcessaEvento` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbfolha`
--

CREATE TABLE `tbfolha` (
  `codFolha` int(11) NOT NULL,
  `tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `dataFolha` date NOT NULL,
  `statusFolha` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbfuncionarios`
--

CREATE TABLE `tbfuncionarios` (
  `codFuncionario` int(11) NOT NULL,
  `nrFuncionario` int(11) NOT NULL,
  `nomeFuncionario` varchar(250) NOT NULL,
  `cpfFuncionario` varbinary(255) NOT NULL,
  `setorFuncionario` varchar(150) NOT NULL,
  `cargoFuncionario` varchar(150) NOT NULL,
  `turnoFuncionario` varchar(255) NOT NULL,
  `cboCargoFuncionario` varchar(7) DEFAULT NULL,
  `dataAdmiFuncionario` date NOT NULL,
  `salarioFuncionario` double NOT NULL,
  `tipoSalarioFuncionario` varchar(30) NOT NULL,
  `adcNoturnoFuncionario` int(11) NOT NULL,
  `insalubriFuncionario` int(11) NOT NULL,
  `utiliTransFuncionario` int(11) NOT NULL,
  `utiliRefFuncionario` int(11) NOT NULL,
  `emailFuncionario` varbinary(155) DEFAULT NULL,
  `celularFuncionario` varbinary(55) DEFAULT NULL,
  `numResidencial` varbinary(55) DEFAULT NULL,
  `statusFuncionario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbintegra`
--

CREATE TABLE `tbintegra` (
  `codIntegra` int(10) UNSIGNED NOT NULL,
  `dataUltiIntegra` date NOT NULL,
  `dataVencIntegra` date NOT NULL,
  `dataUltiAso` date NOT NULL,
  `dataVencAso` date NOT NULL,
  `tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `tbempresas_codEmp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbparcela`
--

CREATE TABLE `tbparcela` (
  `codParcela` int(10) UNSIGNED NOT NULL,
  `valorParcela` double NOT NULL,
  `dataVencParcela` date NOT NULL,
  `statusParcela` int(1) NOT NULL,
  `tbcoparti_codCop` int(11) NOT NULL,
  `tbcoparti_tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `tbcoparti_tbdependentes_codDependente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbpre_user`
--

CREATE TABLE `tbpre_user` (
  `cod` int(11) NOT NULL,
  `tbusuarios_codUser` int(11) NOT NULL,
  `v_dash_dp` int(11) NOT NULL,
  `r_email_v_integral` int(11) NOT NULL,
  `r_email_v_par` int(11) NOT NULL,
  `r_email_v_contra` int(11) NOT NULL,
  `r_email_nf_compras` int(11) NOT NULL,
  `r_email_nf_fiscal` int(11) NOT NULL,
  `r_email_oco_prod` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbprocedi`
--

CREATE TABLE `tbprocedi` (
  `codPro` int(11) NOT NULL,
  `nomePro` varchar(150) NOT NULL,
  `valorPro` double NOT NULL,
  `parcelaPro` int(50) NOT NULL,
  `statusPro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbrealiza`
--

CREATE TABLE `tbrealiza` (
  `codRel` int(11) NOT NULL,
  `tbprocedi_codPro` int(11) NOT NULL,
  `valorProcedimento` double NOT NULL,
  `tbcoparti_codCop` int(11) NOT NULL,
  `tbcoparti_tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `dataPro` date NOT NULL,
  `medicoPro` varchar(155) DEFAULT NULL,
  `localPro` varchar(155) DEFAULT NULL,
  `nTitulo` int(11) DEFAULT NULL,
  `tbcoparti_tbdependentes_codDependente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbrealizafolha`
--

CREATE TABLE `tbrealizafolha` (
  `codRelFolha` int(11) NOT NULL,
  `tbFolha_codFolha` int(11) NOT NULL,
  `tbFolha_tbfuncionarios_codFuncionario` int(11) NOT NULL,
  `tbEventos_codEvento` int(11) NOT NULL,
  `tipoSalarioFuncio` varchar(45) NOT NULL,
  `conteudoFolha` double NOT NULL,
  `tbparcela_codPar` int(11) DEFAULT NULL,
  `tipoProcessaEvento` varchar(55) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbsessao`
--

CREATE TABLE `tbsessao` (
  `codSessao` int(11) NOT NULL,
  `tbusuarios_codUsuario` int(11) DEFAULT NULL,
  `tbtelas_codTela` varchar(155) DEFAULT NULL,
  `fk_versaoAtualTela` varchar(155) DEFAULT NULL,
  `nomeDesktop` varchar(45) DEFAULT NULL,
  `ipDesktop` varchar(45) DEFAULT NULL,
  `dataSessao` date DEFAULT NULL,
  `horaSessao` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbtelas`
--

CREATE TABLE `tbtelas` (
  `codTela` varchar(155) NOT NULL,
  `versaoAtualTela` varchar(155) NOT NULL,
  `dataVersao` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbtestcovid`
--

CREATE TABLE `tbtestcovid` (
  `codTeste` int(11) NOT NULL,
  `dataTeste` date NOT NULL,
  `resultadoTeste` int(1) DEFAULT NULL,
  `tbfuncionarios_codFuncionario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbusuarios`
--

CREATE TABLE `tbusuarios` (
  `codUsuario` int(255) NOT NULL,
  `nomeUsuario` varchar(250) NOT NULL,
  `setorUsuario` varchar(150) NOT NULL,
  `cargoUsuario` varchar(150) NOT NULL,
  `loginUsuario` varchar(45) NOT NULL,
  `senhaUsuario` varbinary(255) DEFAULT NULL,
  `emailUsuario` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `tbusuarios`
--

INSERT INTO `tbusuarios` (`codUsuario`, `nomeUsuario`, `setorUsuario`, `cargoUsuario`, `loginUsuario`, `senhaUsuario`, `emailUsuario`) VALUES
(1, 'admin', 'CPD', 'admin', 'admin', 0xa60167c550765e5b2e399c5b5dfc6f17, 'cpd@metalurgicaimac.com.br');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbversao`
--

CREATE TABLE `tbversao` (
  `idVersao` int(11) NOT NULL,
  `tbtelas_codTela` varchar(155) NOT NULL,
  `novaVersao` varchar(155) NOT NULL,
  `dataLibVersao` date NOT NULL,
  `observaVersao` varchar(999) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipo_arquivo`
--

CREATE TABLE `tipo_arquivo` (
  `cod` int(11) NOT NULL,
  `descricao` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `acesso_user`
--
ALTER TABLE `acesso_user`
  ADD PRIMARY KEY (`cod`),
  ADD UNIQUE KEY `cod_UNIQUE` (`cod`),
  ADD KEY `fk_acesso_user_tbtelas_idx` (`tbtelas_codTela`),
  ADD KEY `fk_acesso_user_tbusuarios1_idx` (`tbusuarios_codUsuario`);

--
-- Índices para tabela `alteracao_rev`
--
ALTER TABLE `alteracao_rev`
  ADD PRIMARY KEY (`cod`),
  ADD UNIQUE KEY `cod_UNIQUE` (`cod`),
  ADD KEY `fk_solicita_rev_item_idx` (`item_cod`),
  ADD KEY `fk_alteracao_rev_tbempresas1_idx` (`tbempresas_codEmp`);

--
-- Índices para tabela `arquivos_alteracao`
--
ALTER TABLE `arquivos_alteracao`
  ADD PRIMARY KEY (`cod`),
  ADD UNIQUE KEY `cod_UNIQUE` (`cod`),
  ADD KEY `fk_alteracao_arquivos_tipo_arquivo1_idx` (`tipo_arquivo_cod`),
  ADD KEY `fk_arquivos_alteracao_alteracao_rev1_idx` (`alteracao_rev_cod`);

--
-- Índices para tabela `cop_nfe_i`
--
ALTER TABLE `cop_nfe_i`
  ADD PRIMARY KEY (`numero_nfe`);

--
-- Índices para tabela `ferramenta`
--
ALTER TABLE `ferramenta`
  ADD PRIMARY KEY (`cod_fer`),
  ADD UNIQUE KEY `cod_UNIQUE` (`cod_fer`),
  ADD UNIQUE KEY `cod_ferramenta_UNIQUE` (`cod_ferramenta`);

--
-- Índices para tabela `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`cod`) USING BTREE,
  ADD UNIQUE KEY `cod_UNIQUE` (`cod`),
  ADD UNIQUE KEY `codItem_UNIQUE` (`cod_item`),
  ADD KEY `fk_item_tbempresas1_idx` (`tbempresas_codEmp`);

--
-- Índices para tabela `maquina`
--
ALTER TABLE `maquina`
  ADD PRIMARY KEY (`cod_maq`),
  ADD UNIQUE KEY `cod_maq_UNIQUE` (`cod_maq`),
  ADD UNIQUE KEY `codigo_maquina_UNIQUE` (`codigo_maquina`);

--
-- Índices para tabela `nfe_diverge`
--
ALTER TABLE `nfe_diverge`
  ADD PRIMARY KEY (`cod_dv`,`fk_nfe_entrada_cod_nfe`),
  ADD UNIQUE KEY `cod_UNIQUE` (`cod_dv`),
  ADD KEY `fk_nfe_diverge_nfe_entrada_idx` (`fk_nfe_entrada_cod_nfe`);

--
-- Índices para tabela `nfe_entrada`
--
ALTER TABLE `nfe_entrada`
  ADD PRIMARY KEY (`cod_nfe`),
  ADD UNIQUE KEY `cod_nfe_UNIQUE` (`cod_nfe`);

--
-- Índices para tabela `ocorrencia_producao`
--
ALTER TABLE `ocorrencia_producao`
  ADD PRIMARY KEY (`cod_bo`),
  ADD UNIQUE KEY `cod_bo_UNIQUE` (`cod_bo`),
  ADD KEY `fk_ocorrencia_producao_ferramenta_idx` (`ferramenta_cod_fer`),
  ADD KEY `fk_ocorrencia_producao_maquina1_idx` (`maquina_cod_maq`),
  ADD KEY `fk_ocorrencia_producao_tbfuncionarios1_idx` (`tbfuncionarios_codFuncionario`),
  ADD KEY `fk_ocorrencia_producao_item1_idx` (`item_cod`);

--
-- Índices para tabela `solicita_alteracao`
--
ALTER TABLE `solicita_alteracao`
  ADD PRIMARY KEY (`cod`),
  ADD UNIQUE KEY `cod_UNIQUE` (`cod`),
  ADD UNIQUE KEY `alteracao_rev_cod_UNIQUE` (`alteracao_rev_cod`),
  ADD KEY `fk_solicita_alteracao_alteracao_rev1_idx` (`alteracao_rev_cod`);

--
-- Índices para tabela `tbavalia`
--
ALTER TABLE `tbavalia`
  ADD PRIMARY KEY (`cod`),
  ADD KEY `tbempresas_codEmp` (`tbempresas_codEmp`);

--
-- Índices para tabela `tbcontacesso`
--
ALTER TABLE `tbcontacesso`
  ADD PRIMARY KEY (`codAcesso`);

--
-- Índices para tabela `tbcontrato`
--
ALTER TABLE `tbcontrato`
  ADD PRIMARY KEY (`codContra`,`tbfuncionarios_codFuncionario`),
  ADD KEY `fk_tbcontrato_tbfuncionarios1_idx` (`tbfuncionarios_codFuncionario`);

--
-- Índices para tabela `tbcontsaida`
--
ALTER TABLE `tbcontsaida`
  ADD PRIMARY KEY (`codSaida`),
  ADD KEY `fk_tbcontsaida_tbfuncionarios_idx` (`tbfuncionarios_codFuncionario`);

--
-- Índices para tabela `tbcoparti`
--
ALTER TABLE `tbcoparti`
  ADD PRIMARY KEY (`codCop`,`tbfuncionarios_codFuncionario`),
  ADD KEY `fk_tbcoparti_tbfuncionarios1` (`tbfuncionarios_codFuncionario`),
  ADD KEY `fk_tbcoparti_tbdependentes1_idx` (`tbdependentes_codDependente`);

--
-- Índices para tabela `tbcotista`
--
ALTER TABLE `tbcotista`
  ADD UNIQUE KEY `fk_tbcotista_tbfuncionarios1_idx` (`tbfuncionarios_codFuncionario`) USING BTREE,
  ADD UNIQUE KEY `codCota` (`codCota`) USING BTREE;

--
-- Índices para tabela `tbdependentes`
--
ALTER TABLE `tbdependentes`
  ADD PRIMARY KEY (`codDependente`),
  ADD UNIQUE KEY `codDependente_UNIQUE` (`codDependente`);

--
-- Índices para tabela `tbempresas`
--
ALTER TABLE `tbempresas`
  ADD PRIMARY KEY (`codEmp`);

--
-- Índices para tabela `tbeventos`
--
ALTER TABLE `tbeventos`
  ADD PRIMARY KEY (`codEvento`);

--
-- Índices para tabela `tbfolha`
--
ALTER TABLE `tbfolha`
  ADD PRIMARY KEY (`codFolha`,`tbfuncionarios_codFuncionario`),
  ADD KEY `fk_tbFolha_tbfuncionarios1_idx` (`tbfuncionarios_codFuncionario`);

--
-- Índices para tabela `tbfuncionarios`
--
ALTER TABLE `tbfuncionarios`
  ADD PRIMARY KEY (`codFuncionario`);

--
-- Índices para tabela `tbintegra`
--
ALTER TABLE `tbintegra`
  ADD PRIMARY KEY (`codIntegra`),
  ADD KEY `fk_tbintegra_tbfuncionarios1_idx` (`tbfuncionarios_codFuncionario`),
  ADD KEY `fk_tbintegra_tbempresas1_idx` (`tbempresas_codEmp`);

--
-- Índices para tabela `tbparcela`
--
ALTER TABLE `tbparcela`
  ADD PRIMARY KEY (`codParcela`),
  ADD KEY `fk_tbparcela_tbcoparti1_idx` (`tbcoparti_codCop`,`tbcoparti_tbfuncionarios_codFuncionario`),
  ADD KEY `fk_tbparcela_tbcoparti2_idx` (`tbcoparti_tbdependentes_codDependente`);

--
-- Índices para tabela `tbpre_user`
--
ALTER TABLE `tbpre_user`
  ADD PRIMARY KEY (`cod`,`tbusuarios_codUser`),
  ADD UNIQUE KEY `cod_UNIQUE` (`cod`),
  ADD KEY `fk_tbpre_user_tbusuario_codUsuario_idx` (`tbusuarios_codUser`);

--
-- Índices para tabela `tbprocedi`
--
ALTER TABLE `tbprocedi`
  ADD PRIMARY KEY (`codPro`);

--
-- Índices para tabela `tbrealiza`
--
ALTER TABLE `tbrealiza`
  ADD PRIMARY KEY (`codRel`),
  ADD KEY `fk_tbprocedi_has_tbcoparti_tbcoparti1_idx` (`tbcoparti_codCop`,`tbcoparti_tbfuncionarios_codFuncionario`),
  ADD KEY `fk_tbprocedi_has_tbcoparti_tbprocedi1_idx` (`tbprocedi_codPro`),
  ADD KEY `tbprocedi_codPro` (`tbprocedi_codPro`,`tbcoparti_codCop`) USING BTREE,
  ADD KEY `fk_tbrealiza_has_tbcoparti_tbdependentes1_idx` (`tbcoparti_tbdependentes_codDependente`);

--
-- Índices para tabela `tbrealizafolha`
--
ALTER TABLE `tbrealizafolha`
  ADD PRIMARY KEY (`codRelFolha`),
  ADD KEY `fk_tbFolha_has_tbEventos_tbEventos1_idx` (`tbEventos_codEvento`),
  ADD KEY `fk_tbFolha_has_tbEventos_tbFolha1_idx` (`tbFolha_codFolha`,`tbFolha_tbfuncionarios_codFuncionario`);

--
-- Índices para tabela `tbsessao`
--
ALTER TABLE `tbsessao`
  ADD PRIMARY KEY (`codSessao`),
  ADD KEY `tbsessao_tbusuarios1_idx` (`tbusuarios_codUsuario`),
  ADD KEY `tbsessao_tbtelas1_idx` (`tbtelas_codTela`);

--
-- Índices para tabela `tbtelas`
--
ALTER TABLE `tbtelas`
  ADD PRIMARY KEY (`codTela`),
  ADD UNIQUE KEY `codTela_UNIQUE` (`codTela`);

--
-- Índices para tabela `tbtestcovid`
--
ALTER TABLE `tbtestcovid`
  ADD PRIMARY KEY (`codTeste`),
  ADD UNIQUE KEY `codTeste_UNIQUE` (`codTeste`),
  ADD KEY `fk_tbtestcovid_tbfuncionarios_idx` (`tbfuncionarios_codFuncionario`);

--
-- Índices para tabela `tbusuarios`
--
ALTER TABLE `tbusuarios`
  ADD PRIMARY KEY (`codUsuario`),
  ADD UNIQUE KEY `codUsuario_UNIQUE` (`codUsuario`);

--
-- Índices para tabela `tbversao`
--
ALTER TABLE `tbversao`
  ADD PRIMARY KEY (`idVersao`),
  ADD UNIQUE KEY `idVersao_UNIQUE` (`idVersao`),
  ADD KEY `fk_tbversao_tbtelas_idx` (`tbtelas_codTela`);

--
-- Índices para tabela `tipo_arquivo`
--
ALTER TABLE `tipo_arquivo`
  ADD PRIMARY KEY (`cod`),
  ADD UNIQUE KEY `cod_UNIQUE` (`cod`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `acesso_user`
--
ALTER TABLE `acesso_user`
  MODIFY `cod` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `arquivos_alteracao`
--
ALTER TABLE `arquivos_alteracao`
  MODIFY `cod` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `ferramenta`
--
ALTER TABLE `ferramenta`
  MODIFY `cod_fer` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `item`
--
ALTER TABLE `item`
  MODIFY `cod` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `maquina`
--
ALTER TABLE `maquina`
  MODIFY `cod_maq` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `nfe_diverge`
--
ALTER TABLE `nfe_diverge`
  MODIFY `cod_dv` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `nfe_entrada`
--
ALTER TABLE `nfe_entrada`
  MODIFY `cod_nfe` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `ocorrencia_producao`
--
ALTER TABLE `ocorrencia_producao`
  MODIFY `cod_bo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `solicita_alteracao`
--
ALTER TABLE `solicita_alteracao`
  MODIFY `cod` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbavalia`
--
ALTER TABLE `tbavalia`
  MODIFY `cod` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbcontacesso`
--
ALTER TABLE `tbcontacesso`
  MODIFY `codAcesso` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbcontrato`
--
ALTER TABLE `tbcontrato`
  MODIFY `codContra` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbcontsaida`
--
ALTER TABLE `tbcontsaida`
  MODIFY `codSaida` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbcoparti`
--
ALTER TABLE `tbcoparti`
  MODIFY `codCop` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbfolha`
--
ALTER TABLE `tbfolha`
  MODIFY `codFolha` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbfuncionarios`
--
ALTER TABLE `tbfuncionarios`
  MODIFY `codFuncionario` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbintegra`
--
ALTER TABLE `tbintegra`
  MODIFY `codIntegra` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbparcela`
--
ALTER TABLE `tbparcela`
  MODIFY `codParcela` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbpre_user`
--
ALTER TABLE `tbpre_user`
  MODIFY `cod` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbrealiza`
--
ALTER TABLE `tbrealiza`
  MODIFY `codRel` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbrealizafolha`
--
ALTER TABLE `tbrealizafolha`
  MODIFY `codRelFolha` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbsessao`
--
ALTER TABLE `tbsessao`
  MODIFY `codSessao` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbtestcovid`
--
ALTER TABLE `tbtestcovid`
  MODIFY `codTeste` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tbversao`
--
ALTER TABLE `tbversao`
  MODIFY `idVersao` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `acesso_user`
--
ALTER TABLE `acesso_user`
  ADD CONSTRAINT `fk_acesso_user_tbtelas` FOREIGN KEY (`tbtelas_codTela`) REFERENCES `tbtelas` (`codTela`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_acesso_user_tbusuarios1` FOREIGN KEY (`tbusuarios_codUsuario`) REFERENCES `tbusuarios` (`codUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `alteracao_rev`
--
ALTER TABLE `alteracao_rev`
  ADD CONSTRAINT `fk_alteracao_rev_tbempresas1` FOREIGN KEY (`tbempresas_codEmp`) REFERENCES `tbempresas` (`codEmp`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_solicita_rev_item` FOREIGN KEY (`item_cod`) REFERENCES `item` (`cod`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `arquivos_alteracao`
--
ALTER TABLE `arquivos_alteracao`
  ADD CONSTRAINT `fk_alteracao_arquivos_tipo_arquivo1` FOREIGN KEY (`tipo_arquivo_cod`) REFERENCES `tipo_arquivo` (`cod`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_arquivos_alteracao_alteracao_rev1` FOREIGN KEY (`alteracao_rev_cod`) REFERENCES `alteracao_rev` (`cod`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `nfe_diverge`
--
ALTER TABLE `nfe_diverge`
  ADD CONSTRAINT `fk_nfe_diverge_nfe_entrada` FOREIGN KEY (`fk_nfe_entrada_cod_nfe`) REFERENCES `nfe_entrada` (`cod_nfe`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
