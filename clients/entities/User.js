const { ApiClient } = require("../apiClient");

const apiClientInner = new ApiClient("http://localhost:8088");

class User {
  id = "";
  name = "";
  email = "";
  password = "";
  token = "";
  devices = [];
  apiClient = apiClientInner;

  constructor(name, email, password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  setDevices = (devices) => {
    this.devices = devices;
  };

  test = async () => {
    try {
      const { data } = await this.apiClient.get("api/v1/auth/token");
      console.log(data);
    } catch (e) {
      console.log("Error", e.data, e.status);
    }
  };

  registration = async () => {
    try {
      console.log(`Registration of user with name ${this.name}`);
      const body = {
        name: this.name,
        email: this.email,
        password: this.password,
      };
      const { data } = await this.apiClient.post(
        "api/v1/auth/registration",
        body
      );
      this.token = data.token;
      this.apiClient.insertToken(data.token);
      console.log(`Successfully registered user with name ${this.name}`);
    } catch (e) {
      console.log("Error", e.data, e.status);
    }
  };

  login = async () => {
    try {
      console.log(`Login of user with name ${this.name}`);
      const { data } = await this.apiClient.post("api/v1/auth/registration", {
        token: this.token,
      });
      this.id = data.id;
      console.log(`Successfully logged in user with name ${this.name}`);
    } catch (e) {
      console.log("Error", e.data, e.status);
    }
  };

  getAllDevices = async () => {
    try {
      console.log(`Get all devices of user with name ${this.name}`);
      const { data } = await this.apiClient.get(
        `api/v1/deviceToUser/getAll/${this.id}`
      );
      console.log(
        `Successfully got all devices from user with name ${this.name}, data ${data}`
      );
    } catch (e) {
      console.log("Error", e.data, e.status);
    }
  };

  getDeviceById = async (uuid) => {
    try {
      console.log(
        `Single device of user with name ${this.name} and id ${uuid}`
      );
      const body = { userID: this.id, deviceID: uuid };
      const { data } = await this.apiClient.post(
        `api/v1/deviceToUser/getDevice`,
        body
      );
      console.log(
        `Successfully got devices from user with name ${this.name}, data ${data}`
      );
    } catch (e) {
      console.log("Error", e.data, e.status);
    }
  };

  connectToDevice = async (uuid) => {
    try {
      console.log(
        `Connect to device of user with name ${this.name} and device id ${uuid}`
      );
      const body = { userID: this.id, deviceID: uuid };
      const { data } = await this.apiClient.post(
        "api/v1/deviceToUser/connect",
        body
      );
      console.log(
        `Successfully connect to device from user with name ${this.name}, data ${data}`
      );
    } catch (e) {
      console.log("Error", e.data, e.status);
    }
  };

  disconnectFromDevice = async (uuid) => {
    try {
      console.log(
        `Disconnect from device of user with name ${this.name} and device id ${uuid}`
      );
      const body = { userID: this.id, deviceID: uuid };
      const { data } = await this.apiClient.post(
        "api/v1/deviceToUser/disconnect",
        body
      );
      console.log(
        `Successfully disconnect to device from user with name ${this.name}, data ${data}`
      );
    } catch (e) {
      console.log("Error", e.data, e.status);
    }
  };

  deleteDeviceManually = async (uuid) => {
    try {
      console.log(
        `Delete device of user with name ${this.name}  and device id ${uuid}`
      );
      const body = { userID: this.id, deviceID: uuid };
      const { data } = await this.apiClient.post(
        "api/v1/deviceToUser/delete",
        body
      );
      console.log(
        `Successfully deleted  device from user with name ${this.name}, data ${data}`
      );
    } catch (e) {
      console.log("Error", e.data, e.status);
    }
  };
}

module.exports = { User };
