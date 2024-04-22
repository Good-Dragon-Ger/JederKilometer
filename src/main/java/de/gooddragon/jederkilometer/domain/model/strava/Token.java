package de.gooddragon.jederkilometer.domain.model.strava;

public record Token(String token_type,
                    String access_token,
                    long expires_at,
                    int expires_in,
                    String refresh_token) {

    @Override
    public String toString() {
        return "Token{" +
                "token_type='" + token_type + '\'' +
                ", access_token='" + access_token + '\'' +
                ", expires_at=" + expires_at +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                '}';
    }
}
