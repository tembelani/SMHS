-- expiring contracts

SELECT c.contract_id, s.first_name, s.last_name, b.building_name, r.room_number, 
       c.end_date, DATEDIFF(c.end_date, CURDATE()) as days_until_expiry
FROM Contracts c
JOIN Students s ON c.student_id = s.student_id
JOIN Rooms r ON c.room_id = r.room_id
JOIN Buildings b ON r.building_id = b.building_id
WHERE c.end_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)
AND c.status = 'active';