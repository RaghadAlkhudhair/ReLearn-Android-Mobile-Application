package com.example.my1stapplication;
import java.security.PublicKey;


    public class User {
        private String ID,username,password,phone,name,email,Iban,bank,addr,owner;
        long time;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public User(){

        }

        public User(String name, String email , String IBAN, String pNo, String ID, String bank, String addr){
            this.email=email;
            this.name=name;
            this.Iban=IBAN;
            this.phone=pNo;
            this.ID=ID;
            this.bank=bank;
            this.addr=addr;
        }

        public User(String displayName, String userEmail, String password, long time){
            name=displayName;
            email=userEmail;
            this.password=password;
            this.time=time;
        }

        public User(String username, String name, String email,String password, long time, String phone){
            this.username=username;
            this.password=password;
            this.phone=phone;
            this.name=name;
            this.email=email;
            this.time=time;
        }

        public String getPassword() {
            return password;
        }
        public String getUsername(){
            return username;
        }
        public String getPhone() { return phone; }
        public String getName() { return name; }
        public long getTime() { return time; }
        public String getEmail() { return email; }
        public String getID() { return ID; }
        public String getIban(){
            return Iban;
        }
        public  void setIban(String IBAN){
            this.Iban=IBAN;
        }
        public void setID(String ID) {
            this.ID = ID;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public void setPhone(String phone) { this.phone = phone; }
        public void setName(String name) { this.name = name; }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setTime(long time) {
            this.time = time;
        }
        public String getBank(){return this.bank;}
        public void setBank(String bnk){
            this.bank=bnk;
        }

    }


