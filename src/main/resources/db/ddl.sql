-- Create a new database called 'Library_Management_System_db'
-- Connect to the 'master' database to run this snippet
USE master
GO
-- Create the new database if it does not exist already
IF NOT EXISTS (
    SELECT [name]
        FROM sys.databases
        WHERE [name] = N'Library_Management_System_db'
)
CREATE DATABASE Library_Management_System_db
GO


-- Create the table in the specified schema
CREATE TABLE [dbo].[book](
    [id] [bigint] NOT NULL,
    [unique_code] [varchar](20) NULL,
    [author] [varchar](50) NULL,
    [language] [varchar](20) NULL,
    [publication_date] [date] NULL,
    [subject] [varchar](50) NULL,
    [title] [varchar](20) NULL
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[book] ADD PRIMARY KEY CLUSTERED
    (
    [id] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
    GO



-- Create the table in the specified schema

CREATE TABLE [dbo].[book_item](
    [id] [bigint] NOT NULL,
    [bar_code] [varchar](255) NULL,
    [due_date] [date] NULL,
    [issue_date] [date] NULL,
    [price] [float] NOT NULL,
    [rack_number] [int] NULL,
    [status] [varchar](255) NULL,
    [book_id] [bigint] NOT NULL,
    [member_id] [bigint] NOT NULL
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[book_item] ADD PRIMARY KEY CLUSTERED
    (
    [id] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[book_item]  WITH CHECK ADD  CONSTRAINT [FK8rv4lky70oknrbd01ph1w9adw] FOREIGN KEY([book_id])
    REFERENCES [dbo].[book] ([id])
    GO
ALTER TABLE [dbo].[book_item] CHECK CONSTRAINT [FK8rv4lky70oknrbd01ph1w9adw]
    GO
ALTER TABLE [dbo].[book_item]  WITH CHECK ADD  CONSTRAINT [FKslbp10y9cgoluj1x621kx95gp] FOREIGN KEY([member_id])
    REFERENCES [dbo].[member] ([id])
    GO
ALTER TABLE [dbo].[book_item] CHECK CONSTRAINT [FKslbp10y9cgoluj1x621kx95gp]
    GO



-- Create the table in the specified schema

CREATE TABLE [dbo].[librarian](
    [id] [bigint] NOT NULL,
    [address] [varchar](100) NOT NULL,
    [email] [varchar](30) NOT NULL,
    [name] [varchar](50) NOT NULL,
    [phone] [varchar](10) NOT NULL
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[librarian] ADD PRIMARY KEY CLUSTERED
    (
    [id] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
    GO


-- Create the table in the specified schema

CREATE TABLE [dbo].[member](
    [id] [bigint] NOT NULL,
    [account_status] [varchar](255) NOT NULL,
    [address] [varchar](100) NOT NULL,
    [email] [varchar](30) NOT NULL,
    [member_till] [datetime2](7) NOT NULL,
    [name] [varchar](50) NOT NULL,
    [phone] [varchar](10) NOT NULL
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[member] ADD PRIMARY KEY CLUSTERED
    (
    [id] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
    GO


-- Create the table in the specified schema
CREATE TABLE [dbo].[fine](
    [id] [bigint] NOT NULL,
    [book_itemid] [bigint] NOT NULL,
    [member_id] [bigint] NOT NULL,
    [total_fine] [float] NULL
) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[fine] ADD PRIMARY KEY CLUSTERED
    (
    [id] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
    GO
