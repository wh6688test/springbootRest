package org.tutorials.wproject1.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.tutorials.wproject1.model.Group;
import org.tutorials.wproject1.model.GroupAttr;
import org.tutorials.wproject1.model.Member;

public interface IGroupService {

    List<Group> findAll();

    Optional<Group>findGroup(Long gid);

    Group findGroupAttr(Long gid);

    Optional<List<Group>> findMemberById(String memberId);

    Group createGroup(Map<String,String> groupAttr);

    void deleteGroup(Group group);

    //void deleteMember(Group group, String memberId);
    List<Group> deleteMember(String memberId);

    Optional<Group> updateGroupAttribute(Long gid, Map<String, String> attr);

    Optional<Group> updateGroupMember(Long gid, Member memberIn);

}
