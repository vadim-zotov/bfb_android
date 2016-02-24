package com.sphereinc.chairlift.api.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sphereinc.chairlift.api.deserialization.DateDeserializer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("about")
    public String about;

    @SerializedName("avatar")
    public Image avatar;

    @SerializedName("employment_start_date")
    public Date employmentStartDate;

    @SerializedName("date_of_birth")
    public Date birthdayDate;

    @SerializedName("college")
    public String college;

    @SerializedName("cell_phone")
    public String cellPhone;

    @SerializedName("business_phone")
    public String businessPhone;

    @SerializedName("personal_email")
    public String personalEmail;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("email")
    public String email;

    @SerializedName("department")
    public Department department;

    @SerializedName("job_role")
    public JobRole jobRole;

    @Expose
    @SerializedName("location")
    public Location location;

    @SerializedName("skills")
    public List<AdditionalSkill> additionalSkills;

    @SerializedName("required_skills")
    public List<RequiredSkill> requiredSkills;

    @SerializedName("interests")
    public List<Interest> interests;

    @SerializedName("linkedin_url")
    public String linkedinURL;

    @SerializedName("twitter_url")
    public String twiterURL;

    @SerializedName("facebook_url")
    public String facebookURL;

    @SerializedName("github_url")
    public String githubURL;

    @SerializedName("emergency_full_name")
    public String emergencyFullName;

    @SerializedName("emergency_phone")
    public String emergencyPhone;

    @SerializedName("emergency_role")
    public String emergencyRole;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public JobRole getJobRole() {
        return jobRole;
    }

    public void setJobRole(JobRole jobRole) {
        this.jobRole = jobRole;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<AdditionalSkill> getAdditionalSkills() {
        if (additionalSkills == null) {
            additionalSkills = new ArrayList<AdditionalSkill>();
        }
        return additionalSkills;
    }

    public void setAdditionalSkills(List<AdditionalSkill> additionalSkills) {
        this.additionalSkills = additionalSkills;
    }

    public List<RequiredSkill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<RequiredSkill> requiredSkills) {
        if (requiredSkills == null) {
            requiredSkills = new ArrayList<RequiredSkill>();
        }
        this.requiredSkills = requiredSkills;
    }

    public List<Interest> getInterests() {
        if (interests == null) {
            interests = new ArrayList<Interest>();
        }
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Date getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(Date employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getLinkedinURL() {
        return linkedinURL;
    }

    public void setLinkedinURL(String linkedinURL) {
        this.linkedinURL = linkedinURL;
    }

    public String getTwiterURL() {
        return twiterURL;
    }

    public void setTwiterURL(String twiterURL) {
        this.twiterURL = twiterURL;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public String getEmergencyFullName() {
        return emergencyFullName;
    }

    public void setEmergencyFullName(String emergencyFullName) {
        this.emergencyFullName = emergencyFullName;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getEmergencyRole() {
        return emergencyRole;
    }

    public void setEmergencyRole(String emergencyRole) {
        this.emergencyRole = emergencyRole;
    }

    public String getUserName() {
        return getFirstName() + " " + getLastName();
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitials() {
        if (getLastName() != null && !getLastName().isEmpty() &&
                getFirstName() != null && !getFirstName().isEmpty()) {
            return getFirstName().substring(0, 1) + "." + getLastName().substring(0, 1) + ".";
        }
        return "";
    }

    public class Location {
        @SerializedName("city")
        private String city;

        @SerializedName("country")
        private String country;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
