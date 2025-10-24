/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Arrays;

/**
 *
 * @author gagan
 */
public enum ProfileEnum {
    ADMIN("Admin"), STUDENT("Student"), FACULTY("Faculty"), REGISTRAR("Registrar");
    
    private final String profile;
    
    private ProfileEnum(String value) {
        profile = value;
    }

    @Override
    public String toString() {
        return this.profile;
    }
    
    public static ProfileEnum fromProfile(String profileString) {
        return Arrays.stream(ProfileEnum.values())
            .filter(profile -> profile.profile.equals(profileString))
            .findFirst().orElse(null);
    }
}
