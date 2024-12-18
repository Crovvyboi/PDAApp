USE [PDADB]
GO
/****** Object:  Table [dbo].[Adres]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Adres](
	[adresID] [int] NOT NULL,
	[postcode] [varchar](50) NOT NULL,
	[straatNaam] [varchar](50) NOT NULL,
	[nummer] [varchar](50) NOT NULL,
	[plaats] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Adres] PRIMARY KEY CLUSTERED 
(
	[adresID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Bestelling]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bestelling](
	[bestellingID] [int] IDENTITY(1,1) NOT NULL,
	[bestellingStatus] [varchar](50) NOT NULL,
	[email] [varchar](50) NOT NULL,
	[plaatsingDatum] [date] NOT NULL,
 CONSTRAINT [PK_Bestelling] PRIMARY KEY CLUSTERED 
(
	[bestellingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BestellingProduct]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BestellingProduct](
	[bpID] [int] IDENTITY(1,1) NOT NULL,
	[bestellingID] [int] NOT NULL,
	[ean] [varchar](50) NOT NULL,
	[aantal] [int] NOT NULL,
 CONSTRAINT [PK_BestellingProduct] PRIMARY KEY CLUSTERED 
(
	[bpID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BestellingStatus]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BestellingStatus](
	[bestellingStatus] [varchar](50) NOT NULL,
 CONSTRAINT [PK_BestellingStatus] PRIMARY KEY CLUSTERED 
(
	[bestellingStatus] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Klant]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Klant](
	[email] [varchar](50) NOT NULL,
	[klantNaam] [varbinary](max) NOT NULL,
	[tel] [varchar](50) NOT NULL,
	[adresID] [int] NOT NULL,
 CONSTRAINT [PK_Klant] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Magazijnlocatie]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Magazijnlocatie](
	[locatieID] [int] IDENTITY(1,1) NOT NULL,
	[locatie] [varchar](50) NOT NULL,
	[ean] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Magazijnlocatie] PRIMARY KEY CLUSTERED 
(
	[locatieID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Merk]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Merk](
	[merkNaam] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Merk] PRIMARY KEY CLUSTERED 
(
	[merkNaam] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Perceel]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Perceel](
	[perceelID] [int] IDENTITY(1,1) NOT NULL,
	[datumAanmaak] [date] NOT NULL,
	[bestellingID] [int] NOT NULL,
	[transporteurID] [int] NOT NULL,
	[adresID] [int] NOT NULL,
 CONSTRAINT [PK_Perceel] PRIMARY KEY CLUSTERED 
(
	[perceelID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[ean] [varchar](50) NOT NULL,
	[naam] [varchar](50) NOT NULL,
	[actueleVoorraad] [int] NOT NULL,
	[verkoopPrijs] [decimal](2, 0) NOT NULL,
 CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[ean] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductDetail]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductDetail](
	[naam] [varchar](50) NOT NULL,
	[productBeschrijving] [varchar](max) NOT NULL,
	[merkNaam] [varchar](50) NOT NULL,
	[productType] [varchar](50) NOT NULL,
 CONSTRAINT [PK_ProductDetail] PRIMARY KEY CLUSTERED 
(
	[naam] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Producttype]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Producttype](
	[productType] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Producttype] PRIMARY KEY CLUSTERED 
(
	[productType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Transporteur]    Script Date: 17-3-2024 15:01:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Transporteur](
	[transporteurID] [int] NOT NULL,
	[naam] [varchar](50) NOT NULL,
	[voorVoegsel] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Transporteur] PRIMARY KEY CLUSTERED 
(
	[transporteurID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Bestelling]  WITH CHECK ADD  CONSTRAINT [FK_Bestelling_BestellingStatus] FOREIGN KEY([bestellingStatus])
REFERENCES [dbo].[BestellingStatus] ([bestellingStatus])
GO
ALTER TABLE [dbo].[Bestelling] CHECK CONSTRAINT [FK_Bestelling_BestellingStatus]
GO
ALTER TABLE [dbo].[Bestelling]  WITH CHECK ADD  CONSTRAINT [FK_Bestelling_Email] FOREIGN KEY([email])
REFERENCES [dbo].[Klant] ([email])
GO
ALTER TABLE [dbo].[Bestelling] CHECK CONSTRAINT [FK_Bestelling_Email]
GO
ALTER TABLE [dbo].[BestellingProduct]  WITH CHECK ADD  CONSTRAINT [Bestelling-BestellingProduct] FOREIGN KEY([bestellingID])
REFERENCES [dbo].[Bestelling] ([bestellingID])
GO
ALTER TABLE [dbo].[BestellingProduct] CHECK CONSTRAINT [Bestelling-BestellingProduct]
GO
ALTER TABLE [dbo].[BestellingProduct]  WITH CHECK ADD  CONSTRAINT [Product-BestellingProduct] FOREIGN KEY([ean])
REFERENCES [dbo].[Product] ([ean])
GO
ALTER TABLE [dbo].[BestellingProduct] CHECK CONSTRAINT [Product-BestellingProduct]
GO
ALTER TABLE [dbo].[Klant]  WITH CHECK ADD  CONSTRAINT [FK_Klant_Adres] FOREIGN KEY([adresID])
REFERENCES [dbo].[Adres] ([adresID])
GO
ALTER TABLE [dbo].[Klant] CHECK CONSTRAINT [FK_Klant_Adres]
GO
ALTER TABLE [dbo].[Magazijnlocatie]  WITH CHECK ADD  CONSTRAINT [FK_Magazijnlocatie_Product] FOREIGN KEY([ean])
REFERENCES [dbo].[Product] ([ean])
GO
ALTER TABLE [dbo].[Magazijnlocatie] CHECK CONSTRAINT [FK_Magazijnlocatie_Product]
GO
ALTER TABLE [dbo].[Perceel]  WITH CHECK ADD  CONSTRAINT [FK_Perceel_Adres] FOREIGN KEY([adresID])
REFERENCES [dbo].[Adres] ([adresID])
GO
ALTER TABLE [dbo].[Perceel] CHECK CONSTRAINT [FK_Perceel_Adres]
GO
ALTER TABLE [dbo].[Perceel]  WITH CHECK ADD  CONSTRAINT [FK_Perceel_Bestelling] FOREIGN KEY([bestellingID])
REFERENCES [dbo].[Bestelling] ([bestellingID])
GO
ALTER TABLE [dbo].[Perceel] CHECK CONSTRAINT [FK_Perceel_Bestelling]
GO
ALTER TABLE [dbo].[Perceel]  WITH CHECK ADD  CONSTRAINT [FK_Perceel_Transporteur] FOREIGN KEY([transporteurID])
REFERENCES [dbo].[Transporteur] ([transporteurID])
GO
ALTER TABLE [dbo].[Perceel] CHECK CONSTRAINT [FK_Perceel_Transporteur]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK_Product_ProductDetail] FOREIGN KEY([naam])
REFERENCES [dbo].[ProductDetail] ([naam])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK_Product_ProductDetail]
GO
ALTER TABLE [dbo].[ProductDetail]  WITH CHECK ADD  CONSTRAINT [FKMerk] FOREIGN KEY([merkNaam])
REFERENCES [dbo].[Merk] ([merkNaam])
GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FKMerk]
GO
ALTER TABLE [dbo].[ProductDetail]  WITH CHECK ADD  CONSTRAINT [FKType] FOREIGN KEY([productType])
REFERENCES [dbo].[Producttype] ([productType])
GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FKType]
GO
