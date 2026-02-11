INSERT INTO contractor (email, first_name, last_name, phone_number, savings_rate, tax_rate)
select
    'ipdbuddy@demo.com',
    'demo',
    'user',
    4031234567,
    2.0,
    2.0
    WHERE NOT EXISTS(
    SELECT 1 FROM contractor WHERE email = 'ipdbuddy@demo.com'
);



