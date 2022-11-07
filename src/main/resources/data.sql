-- call next value for hibernate_sequence;
insert into user (`id`, `email`, `password`, `phoneNumber`, `name`, `birthDate`, `country`, `gender`, `age`, `enabled`, `created_at`, `updated_at`) values (1, 'steve@naver.com', '1111', '12341234', 'steve', '19950315', 'america','Man', '24', true, now(), now());

-- insert into user (`id`, `name`, `email`, `password`, `student_number`, `major`, `enabled`, `created_at`, `updated_at`) values (2, 'tom', 'tom@naver.com', '2222', '22332211', 'software', 'true', now(), now());

-- insert into pcroom (`id`, `name`, `building_number`, `layer`, `all_seat_number`, `broken_seat_number`, `useable_seat_number`, `in_use_seat_number`, `enabled`) values (1, '6층 PC실', '208', '6', '5', '1', '2', '2', 'true');

-- insert into pcroom (`id`, `name`, `building_number`, `layer`, `all_seat_number`, `pc_seat_number`, `pc_seat_broken_number`, `pc_seat_in_use_number`, `pc_seat_useable_number`,
--     `notebook_seat_number`, `notebook_seat_broken_number`, `notebook_seat_in_use_number`, `notebook_seat_useable_number`, `enabled` ,`created_at`, `updated_at`) values (1, '6층 PC실', '208', '6', '10', '5', '0', '2','3', '5', '0', '2','3', 'true', now(), now());

-- insert into conferenceroom (`id`, `name`, `building_number`, `location_name`, `layer`, `limit`, `all_booked`, `enabled` ,`created_at`, `updated_at`) values (1, '팀플실 1', 208, '6층 PC실', 6, 6, false, true, now(), now());