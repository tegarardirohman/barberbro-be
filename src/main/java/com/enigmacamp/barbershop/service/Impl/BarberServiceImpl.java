package com.enigmacamp.barbershop.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.model.dto.request.BarberRequest;
import com.enigmacamp.barbershop.model.dto.response.BarberResponse;
import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.repository.BarbersRepository;
import com.enigmacamp.barbershop.service.BarberService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BarberServiceImpl implements BarberService {

    private final BarbersRepository barbersRepository;
    private final JwtHelpers jwtHelpers;
    private final EntityManager entityManager;

    @Override
    public BarberResponse create(HttpServletRequest srvrequest, BarberRequest request) {
        try {

            Users user = jwtHelpers.getUser(srvrequest);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }

            Barbers barbers = Barbers.builder()
                    .name(request.getName())
                    .contact_number(request.getContact_number())
                    .email(request.getEmail())
                    .street_address(request.getStreet_address())
                    .state_province_region(request.getState_province_region())
                    .postal_zip_code(request.getPostal_zip_code())
                    .country(request.getCountry())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .description(request.getDescription())
                    .userId(user)
                    .balance(0)
                    .verified(false)
                    .createdAt(System.currentTimeMillis())
                    .updateAt(System.currentTimeMillis())
                    .city(request.getCity())
                    .barbershop_profile_picture_id(request.getBarbershop_profile_picture_id())
                    .build();

            barbers = barbersRepository.save(barbers);

            return barbers.toResponse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Barbers getByEmail(String email) {
        Barbers barbers = barbersRepository.findByEmail(email);
        if (barbers != null && barbers.getDeletedAt() == null) {
            return barbers;
        }

        return null;
    }

    @Override
    public Barbers update(HttpServletRequest srvrequest, BarberRequest request) {
        try {
            Users user = jwtHelpers.getUser(srvrequest);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
            Barbers barbers = barbersRepository.getById(request.getId());
            if (barbers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barber not found");
            }
            barbers.setName(request.getName());
            barbers.setContact_number(request.getContact_number());
            barbers.setEmail(request.getEmail());
            barbers.setStreet_address(request.getStreet_address());
            barbers.setState_province_region(request.getState_province_region());
            barbers.setPostal_zip_code(request.getPostal_zip_code());
            barbers.setCountry(request.getCountry());
            barbers.setLatitude(request.getLatitude());
            barbers.setLongitude(request.getLongitude());
            barbers.setDescription(request.getDescription());
            barbers.setUserId(user);
            barbers.setUpdateAt(System.currentTimeMillis());
            barbers.setCity(request.getCity());
            barbers.setBarbershop_profile_picture_id(request.getBarbershop_profile_picture_id());
            return barbersRepository.save(barbers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BarberResponse update(Barbers barbers) {

        try {
            barbers = barbersRepository.save(barbers);

            return getById(barbers.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BarberResponse> getAll() {
        String sql = "SELECT " +
                "b.id, " +
                "b.name, " +
                "b.contact_number, " +
                "b.email, " +
                "b.street_address, " +
                "b.city, " +
                "b.state_province_region, " +
                "b.postal_zip_code, " +
                "b.country, " +
                "b.latitude, " +
                "b.longitude, " +
                "b.description, " +
                "b.balance, " +
                "b.verified, " +
                "b.created_at, " +
                "b.updated_at, " +
                "AVG(r.rating) AS average_rating, " +
                "COUNT(r.id) AS review_count " +
                "FROM m_barbers b " +
                "LEFT JOIN m_bookings bo ON b.id = bo.barber_id " +
                "LEFT JOIN m_reviews r ON bo.booking_id = r.booking_id " +
                "GROUP BY b.id, b.name, b.contact_number, b.email, b.street_address, " +
                "b.city, b.state_province_region, b.postal_zip_code, b.country, b.latitude, " +
                "b.longitude, b.description, b.balance, b.verified, b.created_at, b.updated_at " +
                "ORDER BY average_rating DESC NULLS LAST;";

        Query query = entityManager.createNativeQuery(sql);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        return results.stream().map(result -> {
            @SuppressWarnings("deprecation")
            Barbers barbers = barbersRepository.getById((String) result[0]);

            if (barbers != null && barbers.getDeletedAt() != null) {
                return null;
            }

            BarberResponse response = barbers.toResponse();
            response.setAverageRating(result[16] == null ? 0 : ((BigDecimal) result[16]).doubleValue());
            response.setReviewCount((Long) result[17]);

            return response;
        }).toList();
    }

    @Override
    public BarberResponse getById(String id) {
        try {
            String sql = "SELECT " +
                    "b.id, " +
                    "b.name, " +
                    "b.contact_number, " +
                    "b.email, " +
                    "b.street_address, " +
                    "b.city, " +
                    "b.state_province_region, " +
                    "b.postal_zip_code, " +
                    "b.country, " +
                    "b.latitude, " +
                    "b.longitude, " +
                    "b.description, " +
                    "b.balance, " +
                    "b.verified, " +
                    "b.created_at, " +
                    "b.updated_at, " +
                    "AVG(r.rating) AS average_rating, " +
                    "COUNT(r.id) AS review_count " +
                    "FROM m_barbers b " +
                    "LEFT JOIN m_bookings bo ON b.id = bo.barber_id " +
                    "LEFT JOIN m_reviews r ON bo.booking_id = r.booking_id " +
                    "WHERE b.id = :barberId " +
                    "GROUP BY b.id, b.name, b.contact_number, b.email, " +
                    "b.street_address, b.city, b.state_province_region, " +
                    "b.postal_zip_code, b.country, b.latitude, b.longitude, " +
                    "b.description, b.balance, b.verified, b.created_at, b.updated_at " +
                    "ORDER BY average_rating DESC";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("barberId", id);

            Object[] result = (Object[]) query.getSingleResult();
            Barbers barbers = barbersRepository.findById(result[0].toString()).orElse(null);

            if (barbers == null) {
                return null;
            }

            if (barbers != null && barbers.getDeletedAt() != null) {
                return null;
            }

            BarberResponse response = barbers.toResponse();
            response.setAverageRating(result[16] == null ? 0 : ((BigDecimal) result[16]).doubleValue());
            response.setReviewCount((Long) result[17]);

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Barbers getByUserId(Users user) {
        try {
            return barbersRepository.findByUserId(user).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BarberResponse> getByNearBy(double latitude, double longitude) {
        try {
            Double distance = 5.0;

            String sql = "SELECT " +
                    "b.id, " +
                    "b.name, " +
                    "b.street_address, " +
                    "b.city, " +
                    "b.state_province_region, " +
                    "b.country, " +
                    "b.latitude, " +
                    "b.longitude, " +
                    "b.balance, " +
                    "b.description, " +
                    "b.email, " +
                    "b.contact_number, " +
                    "b.created_at, " +
                    "b.postal_zip_code, " +
                    "b.verified, " +
                    "b.updated_at, " +
                    "distance_km, " +
                    "AVG(r.rating) AS average_rating, " +
                    "COUNT(r.id) AS review_count " +
                    "FROM ( " +
                    "    SELECT id, name, street_address, city, state_province_region, country, latitude, longitude, balance, description, email, contact_number, created_at, postal_zip_code, verified, updated_at, "
                    +
                    "           (6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + "
                    +
                    "           sin(radians(:latitude)) * sin(radians(latitude))) " +
                    "           ) AS distance_km " +
                    "    FROM m_barbers " +
                    ") AS b " +
                    "LEFT JOIN m_bookings bo ON b.id = bo.barber_id " +
                    "LEFT JOIN m_reviews r ON bo.booking_id = r.booking_id " +
                    "WHERE distance_km <= :distance " +
                    "GROUP BY " +
                    "b.id, " +
                    "b.name, " +
                    "b.street_address, " +
                    "b.city, " +
                    "b.state_province_region, " +
                    "b.country, " +
                    "b.latitude, " +
                    "b.longitude, " +
                    "b.balance, " +
                    "b.description, " +
                    "b.email, " +
                    "b.contact_number, " +
                    "b.created_at, " +
                    "b.postal_zip_code, " +
                    "b.verified, " +
                    "b.updated_at, " +
                    "distance_km " +
                    "ORDER BY distance_km ASC";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("latitude", latitude);
            query.setParameter("longitude", longitude);
            query.setParameter("distance", distance);

            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();

            List<BarberResponse> barbersList = new ArrayList<>();
            for (Object[] result : results) {

                Barbers barbers = barbersRepository.findById((String) result[0]).orElse(null);
                if (barbers == null) {
                    continue;
                }

                if (barbers != null && barbers.getDeletedAt() != null) {
                    continue;
                }

                BarberResponse response = barbers.toResponse();
                response.setAverageRating(result[17] == null ? 0 : ((BigDecimal) result[17]).doubleValue());
                response.setReviewCount((Long) result[18]);
                response.setDistanceKm(result[16] == null ? 0 : (Double) result[16]);
                barbersList.add(response);

            }

            return barbersList;

            // return barbersRepository.findNearbyBarbers(radius, radius, radius);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BarberResponse getCurrentBarber(Users user) {
        try {

            Barbers barber = barbersRepository.findByUserId(user).orElse(null);

            if (barber == null) {
                return null;
            }

            if (barber.getDeletedAt() != null) {
                return null;
            }

            String sql = "SELECT " +
                    "b.id, " +
                    "b.name, " +
                    "b.contact_number, " +
                    "b.email, " +
                    "b.street_address, " +
                    "b.city, " +
                    "b.state_province_region, " +
                    "b.postal_zip_code, " +
                    "b.country, " +
                    "b.latitude, " +
                    "b.longitude, " +
                    "b.description, " +
                    "b.balance, " +
                    "b.verified, " +
                    "b.created_at, " +
                    "b.updated_at, " +
                    "AVG(r.rating) AS average_rating, " +
                    "COUNT(r.id) AS review_count " +
                    "FROM m_barbers b " +
                    "LEFT JOIN m_bookings bo ON b.id = bo.barber_id " +
                    "LEFT JOIN m_reviews r ON bo.booking_id = r.booking_id " +
                    "WHERE b.id = :barberId " +
                    "GROUP BY b.id, b.name, b.contact_number, b.email, " +
                    "b.street_address, b.city, b.state_province_region, " +
                    "b.postal_zip_code, b.country, b.latitude, b.longitude, " +
                    "b.description, b.balance, b.verified, b.created_at, b.updated_at " +
                    "ORDER BY average_rating DESC";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("barberId", barber.getId());

            Object[] result = (Object[]) query.getSingleResult();
            Barbers barbers = barbersRepository.findById(result[0].toString()).orElse(null);

            if (barbers == null) {
                return null;
            }

            BarberResponse response = barbers.toResponse();
            response.setAverageRating(result[16] == null ? 0 : ((BigDecimal) result[16]).doubleValue());
            response.setReviewCount((Long) result[17]);

            return response;

            // return barbersRepository.findByUserId(user).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Barbers getBarberById(String id) {
        try {
            Barbers barbers = barbersRepository.findById(id).orElse(null);

            if (barbers == null) {
                return null;
            }
            
            if (barbers != null && barbers.getDeletedAt() != null) {
                return null;
            }

            return barbers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean delete(String id) {
        try {
            Barbers barbers = barbersRepository.findById(id).orElse(null);
            if (barbers == null) {
                return false;
            }

            barbers.setDeletedAt((Long) System.currentTimeMillis());

            barbersRepository.save(barbers);
            // barbersRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
