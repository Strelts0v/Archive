package com.gv.archive.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("dossier")
/**
 * represents address model
 */
public class Dossier {

    @XStreamAlias("login")
    @XStreamAsAttribute
    /** login attribute */
    private String login;

    @XStreamAlias("role")
    @XStreamAsAttribute
    /** role attribute */
    private Role role;

    @XStreamAlias("name")
    /** name property */
    private String name;

    @XStreamAlias("address")
    /** address property */
    private Address address = new Address();

    @XStreamAlias("mobile")
    /** mobile property */
    private String mobile;

    @XStreamAlias("skype")
    /** skype property */
    private String skype;

    @XStreamAlias("experience")
    /** experience property */
    private String experience;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String toString(){
        return  "name: " + name +
                "\nlogin: " + login +
                "\nrole: " + role +
                "\ncountry: " + address.getCountry() +
                "\ncity: " + address.getCity() +
                "\nstreet: " + address.getStreet() +
                "\nmobile: " + mobile +
                "\nskype: " + skype +
                "\nexperience: " + experience;
    }
}
