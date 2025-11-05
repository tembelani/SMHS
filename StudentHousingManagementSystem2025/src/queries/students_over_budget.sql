-- Students over budget

SELECT s.student_id, s.first_name, s.last_name, s.max_budget, 
       c.monthly_rent, (c.monthly_rent - s.max_budget) as over_budget_amount,
       b.building_name, r.room_number
FROM Students s
JOIN Contracts c ON s.student_id = c.student_id
JOIN Rooms r ON c.room_id = r.room_id
JOIN Buildings b ON r.building_id = b.building_id
WHERE c.monthly_rent > s.max_budget
AND c.status = 'active';