package org.example.API;

import org.example.model.Employee;
import org.example.model.Location;
import org.example.model.Place;
import org.example.model.User;
import org.example.services.BaiduMapService;
import org.example.services.EmployeeService;
import org.example.services.LocationService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //返回下面方法的返回值
@RequestMapping(value = "api")
public class Controller {

    private EmployeeService employeeService;
    private UserService userService;
    private LocationService locationService;
    private BaiduMapService baiduMapService;

    @Autowired
    public Controller(EmployeeService employeeService,
                      UserService userService,
                      LocationService locationService,
                      BaiduMapService baiduMapService) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.locationService = locationService;
        this.baiduMapService = baiduMapService;
    }
    @GetMapping("hello")
    public String hello(@RequestParam("test") String msg){
        return msg;
    }
    @GetMapping("employee/selectAllEmployee")
    public List<Employee> selectAllEmployee(){
        return employeeService.selectAllEmployee();
    }
    @GetMapping("employee/selectEmployeeById")
    public Optional<Employee> selectEmployeeById(@RequestParam("employeeId") String employeeId){
        return employeeService.selectEmployeeById(employeeId);
    }
    @GetMapping("employee/selectEmployeeByName")
    public Optional<Employee> selectEmployeeByName(@RequestParam("employeeName") String employeeName){
        return employeeService.selectEmployeeByName(employeeName);
    }
    @PostMapping("employee/insertEmployee")
    public String insertEmployee(@RequestBody Employee employee){
        return employeeService.insertEmployee(employee);
    }
    @PutMapping("employee/updateEmployee")
    public String updateEmployee(
            @RequestParam("oldName") String oldName,
            @RequestBody Employee employee) {
        return employeeService.updateEmployee(oldName,employee);
    }
    @DeleteMapping("employee/deleteEmployeeById")
    public String deleteEmployeeById( @RequestParam("employeeId") String employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }
    @DeleteMapping("employee/deleteEmployeeByName")
    public String deleteEmployeeByName(@RequestParam("employeeName") String employeeName) {
        return employeeService.deleteEmployeeByName(employeeName);
    }
    //------------用户登录部分
    //
    //
    //
    @GetMapping("user/selectUserByName")
    public Optional<User> selectUserByName(@RequestParam("userName") String userName){
        return userService.selectUserByName(userName);
    }
    @PostMapping("user/insertUser")
    public boolean insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }
    @PostMapping("user/verify")
    public boolean verify(@RequestBody User user){
        return userService.verify(user);
    }
    //
    //
    //
    //--------位置部分
    @GetMapping("location/selectLocationById")
    public Optional<Location> selectLocationById(@RequestParam("locationId") String locationId) {
        return locationService.selectLocationById(locationId);
    }
    @GetMapping("location/selectLocationByName")
    public Optional<Location> selectLocationByName(@RequestParam("locationName") String locationName) {
        return locationService.selectLocationByName(locationName);
    }
    @PostMapping("location/insertLocation")
    public boolean insertLocation(@RequestBody Location location) {
        return locationService.insertLocation(location);
    }
    @PutMapping("location/updateLocation")
    public boolean updateLocation(@RequestParam("locationId") String locationId,
                                  @RequestBody Location location) {
        return locationService.updateLocation(locationId,location);
    }
    @DeleteMapping("location/deleteLocation")
    public boolean deleteLocation(@RequestParam("locationId") String locationId) {
        return locationService.deleteLocation(locationId);
    }
    //----------
    ///
    //
    //
    //获取地点经纬度
    @GetMapping("findPlace")
    public Place findPlace(@RequestParam("locationName") String locationName,
                           @RequestParam("region") String region){
        Place place = baiduMapService.find(locationName,region);
        return place;
    }
    @GetMapping("getDistance")
    public int getDistance(@RequestParam("latitude") String latitude,
                           @RequestParam("longitude") String longitude){
        return baiduMapService.getDistance(latitude,longitude);
    }
}
