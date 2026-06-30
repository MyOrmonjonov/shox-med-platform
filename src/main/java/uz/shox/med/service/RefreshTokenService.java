package uz.shox.med.service;

import uz.shox.med.entity.RefreshToken;
import uz.shox.med.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void revokeToken(String token);

    void deleteByUser(User user);
}