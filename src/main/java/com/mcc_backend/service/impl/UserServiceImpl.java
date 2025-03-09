//package com.mcc_backend.service.impl;
//
//import com.mcc_backend.repository.UserRepository;
//import com.mcc_backend.service.UserService;
//import com.mcc_backend.util.CustomCheckedException;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static com.mcc_backend.util.CommonConstants.*;
//
//
//@Service
//public class UserServiceImpl implements UserService, UserDetailsService {
//
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    private UserRepository userRepository;
//    private CommonEmailMstRepository commonEmailMstRepository;
//    private CommonEmailTemplateRepository commonEmailTemplateRepository;
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    public void setUserMstRepository(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Autowired
//    public void setCommonEmailMstRepository(CommonEmailMstRepository commonEmailMstRepository) {
//        this.commonEmailMstRepository = commonEmailMstRepository;
//    }
//
//    @Autowired
//    public void setCommonEmailTemplateRepository(CommonEmailTemplateRepository commonEmailTemplateRepository) {
//        this.commonEmailTemplateRepository = commonEmailTemplateRepository;
//    }
//
//
//    @Autowired
//    public void setJwtUtil(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//
//    @Value(CONFIRM_USER_EMAIL_URL)
//    private String confirmUserEmailUrl;
//    @Value(LOGIN_URL)
//    private String loginUrl;
//
//
//    /**
//     * This method is used to send confirmation email to user
//     *
//     * @param userEnd user details
//     */
//    private void sendConfirmationEmail(UserEnt userEnd) throws CustomCheckedException {
//
//        Optional<CommonEmailTemplate> templateOpt = commonEmailTemplateRepository.findById(EMAIL_TEMPLATE_CONFIGURE_USER);
//
//        if (templateOpt.isEmpty()) {
//            throw new CustomCheckedException("EMAIL_TEMPLATE_NOT_FOUND");
//        }
//
//        CommonEmailTemplate emailTemplate = templateOpt.get();
//
//        Document html = Jsoup.parse(emailTemplate.getTemplateData(), CHARACTER_TYPE);
//
//        Element emailSendToElement = html.body().getElementById(PARAM_EMAIL_SEND_TO);
//        emailSendToElement.html(userEnd.getFirstName().concat(EMPTY_SPACE_STRING).concat(userEnd.getLastName() == null ? EMPTY_STRING : userMst.getLastName()));
//
//        Element configurationUrlElement = html.body().getElementById(PARAM_CONFIGURATION_URL);
//        configurationUrlElement.attr(HREF_ATTR, confirmUserEmailUrl.replace(PARAM_UUID, userEnd.getId()));
//
//        CommonEmailMst commonEmailMst = new CommonEmailMst(userEnd.getEmail(), emailTemplate.getSubject(), html.html());
//        commonEmailMstRepository.save(commonEmailMst);
//    }
//
//
//    /**
//     * This method is used to change user status
//     *
//     * @param userId userId that needs to be changed the status
//     * @param status Active, Inactive or Deleted
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public ResponseEntity<Object> changeUserStatus(Integer userId, Character status) throws BMSCheckedException {
//        if (userId == null) {
//            throw new BMSCheckedException(USER_ID_CANNOT_BE_EMPTY);
//        }
//
//        if (status == null) {
//            throw new BMSCheckedException(USER_STATUS_CANNOT_BE_EMPTY);
//        }
//
//        UserMst existingUser = getExistingUser(userId);
//
//        UserMst user = (UserMst) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (user.getUsername().equals(existingUser.getUsername())) {
//            throw new BMSCheckedException(USER_STATUS_CHANGE_NOT_ALLOWED);
//        }
//
//        existingUser.setStatus(status);
//        setUsersUpdatedMetaData(existingUser);
//
//        userMstRepository.save(existingUser);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    /**
//     * This method is used to retrieve user details related to provided user id
//     *
//     * @param userId user id
//     * @return HttpStatus 200 with user details
//     */
//    @Override
//    public ResponseEntity<Object> getUserDetails(Integer userId) {
//        return new ResponseEntity<>(getExistingUser(userId), HttpStatus.OK);
//    }
//
//
//
//}
