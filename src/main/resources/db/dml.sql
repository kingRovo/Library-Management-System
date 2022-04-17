
-- Insert rows into table 'book' in schema '[dbo]'
INSERT INTO [Library_Management_System_db].[dbo].[book]
( -- Columns to insert data into
    [id], [unique_code], [author],[language],[publication_date],[subject],[title]
)
VALUES
    ( -- First row: values for the columns in the list above
    1,'dsfhjtret43sa','updesh','english','02-05-2022','Java','Java for Developers'
    ),
    ( -- Second row: values for the columns in the list above
    2,'dsfhjtret43sfsd','Raj','english','02-05-2022','Software','Clean Code'
    )
    -- Add more rows here
    GO




    -- Insert rows into table 'book_item' in schema '[dbo]'
INSERT INTO [dbo].[book_item]
( -- Columns to insert data into
    id, [bar_code], [issue_date],[price],[rack_number],[status],[book_id],[member_id]
)
VALUES
    ( -- First row: values for the columns in the list above
    1,'sdf34543iu534nbrwebrn3223432','12-09-2020',3000,56,'Available',1,1
    ),
    ( -- Second row: values for the columns in the list above
    2,'sdf34543iu534sdsd3223432','12-09-2022',3000,56,'Available',2,2
    )
    -- Add more rows here
    GO





-- Insert rows into table 'librarian' in schema '[dbo]'
INSERT INTO [dbo].[librarian]
( -- Columns to insert data into
    [id], [address], [email],[name],[phone]
)
VALUES
    ( -- First row: values for the columns in the list above
    1,'noida sector 16 GB nagar 20301', 'mail@mail.com','Raj','7457031111'
    ),
    ( -- Second row: values for the columns in the list above
    2,'noida sector 16 GB nagar 20301', 'mail1@mail.com','Ram','7457031561'
    )
-- Add more rows here
    GO


-- Insert rows into table 'member' in schema '[dbo]'
INSERT INTO [dbo].[member]
( -- Columns to insert data into
    [id], [account_status], [address],[email],[member_till],[name],[phone]
)
VALUES
    ( -- First row: values for the columns in the list above
    1,'active','sector 44 noida UP, 201301','mail@mail.com','01-03-2022','rahul','1234567890'
    ),
    ( -- Second row: values for the columns in the list above
    2,'active','sector 44 noida UP, 201301','mail1@mail.com','01-03-2022','rovo','1223456789'
    )
    -- Add more rows here
    GO