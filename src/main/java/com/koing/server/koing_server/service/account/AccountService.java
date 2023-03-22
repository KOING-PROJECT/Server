package com.koing.server.koing_server.service.account;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.account.Account;
import com.koing.server.koing_server.domain.account.repository.AccountRepository;
import com.koing.server.koing_server.domain.account.repository.AccountRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.account.component.SEED_CBC;
import com.koing.server.koing_server.service.account.dto.AccountCreateDto;
import com.koing.server.koing_server.service.account.dto.AccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final AccountRepositoryImpl accountRepositoryImpl;
    private final UserRepositoryImpl userRepositoryImpl;
    private final UserRepository userRepository;
    private final SEED_CBC seedCbc;

    @Transactional
    public SuperResponse createAccount(AccountCreateDto accountCreateDto) {
        LOGGER.info("[AccountService] 계좌 정보 생성 시도");

        if (accountRepositoryImpl.findAccountByUserId(accountCreateDto.getUserId()) != null) {
            throw new NotAcceptableException("해당 유저의 계좌 정보가 이미 존재합니다.", ErrorCode.NOT_ACCEPTABLE_ACCOUNT_ALREADY_EXIST_EXCEPTION);
        }

        User user = getUser(accountCreateDto.getUserId());
        LOGGER.info("[AccountService] 계좌 주인 조회 성공");

        Account account = Account.builder()
                .bankName(accountCreateDto.getBankName())
                .accountNumber(seedCbc.encrypt(accountCreateDto.getAccountNumber()))
                .birthDate(seedCbc.encrypt(accountCreateDto.getBirthDate()))
                .registrationNumber(seedCbc.encrypt(accountCreateDto.getRegistrationNumber()))
                .build();

        account.setOwner(user);
        LOGGER.info("[AccountService] 계좌 entity 생성 성공");

        Account savedAccount = accountRepository.save(account);

        if (savedAccount == null) {
            throw new DBFailException("계좌 정보 생성 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_ACCOUNT_EXCEPTION);
        }

        User updatedUser = userRepository.save(user);

        if (updatedUser.getAccount() == null) {
            throw new DBFailException("유저 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_UPDATE_USER_FAIL_EXCEPTION);
        }

        return SuccessResponse.success(SuccessCode.ACCOUNT_CREATE_SUCCESS, null);
    }

    @Transactional
    public SuperResponse getAccountInfo(Long userId) {
        LOGGER.info("[AccountService] 계좌 정보 조회 시도");

        Account account = getAccount(userId);
        LOGGER.info("[AccountService] 계좌 정보 조회 성공");

        AccountResponseDto accountResponseDto = new AccountResponseDto(
                account.getBankName(),
                seedCbc.decrypt(account.getAccountNumber()),
                seedCbc.decrypt(account.getBirthDate()),
                seedCbc.decrypt(account.getRegistrationNumber())
        );

        return SuccessResponse.success(SuccessCode.GET_ACCOUNT_SUCCESS, accountResponseDto);
    }

    @Transactional
    public SuperResponse updateAccountInfo(AccountCreateDto accountCreateDto) {
        LOGGER.info("[AccountService] 업데이트 할 계좌 정보 조회 시도");

        Account account = getAccount(accountCreateDto.getUserId());
        LOGGER.info("[AccountService] 업데이트 할 계좌 정보 조회 성공");

        String previousBankName = account.getBankName();
        String previousAccountNumber = account.getAccountNumber();
        String previousBirthDate = account.getBirthDate();
        String previousRegistrationNumber = account.getRegistrationNumber();

        if (accountCreateDto.getBankName().equals(previousBankName)
                && accountCreateDto.getAccountNumber().equals(seedCbc.decrypt(previousAccountNumber))
                && accountCreateDto.getBirthDate().equals(seedCbc.decrypt(previousBirthDate))
                && accountCreateDto.getRegistrationNumber().equals(seedCbc.decrypt(previousRegistrationNumber))
        ) {
            throw new NotAcceptableException("수정할 계좌 정보가 없습니다.", ErrorCode.NOT_ACCEPTABLE_SAME_ACCOUNT_EXCEPTION);
        }

        account.setBankName(accountCreateDto.getBankName());
        account.setAccountNumber(seedCbc.encrypt(accountCreateDto.getAccountNumber()));
        account.setBankName(seedCbc.encrypt(accountCreateDto.getBirthDate()));
        account.setBankName(seedCbc.encrypt(accountCreateDto.getRegistrationNumber()));

        Account updatedAccount = accountRepository.save(account);

        if (previousBankName.equals(updatedAccount.getBankName())
                && seedCbc.decrypt(previousAccountNumber).equals(seedCbc.decrypt(updatedAccount.getAccountNumber()))
                && seedCbc.decrypt(previousBirthDate).equals(seedCbc.decrypt(updatedAccount.getBirthDate()))
                && seedCbc.decrypt(previousRegistrationNumber).equals(seedCbc.decrypt(updatedAccount.getRegistrationNumber()))
        ) {
            throw new DBFailException("계좌 정보 업데이트 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPDATE_ACCOUNT_EXCEPTION);
        }

        return SuccessResponse.success(SuccessCode.ACCOUNT_UPDATE_SUCCESS, null);
    }

    private User getUser(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

    private Account getAccount(Long userId) {
        Account account = accountRepositoryImpl.findAccountByUserId(userId);
        if (account == null) {
            throw new NotFoundException("해당 유저의 계좌 정보를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_ACCOUNT_EXCEPTION);
        }

        return account;
    }

}
