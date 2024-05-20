/* 게시글 삭제 기능 수행 */
function deletePost(postId) {
    fetch('/api/posts/' + postId, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(response => {
        if (!response.ok) {
            throw new Error('네트워크 응답이 실패했습니다.');
        }
        return response.text(); // 응답을 텍스트로 처리
    }).then(message => {
        alert(message); // 서버로부터 받은 메시지를 직접 출력
        window.location.href = '/';
    }).catch(error => {
        console.error('게시글 삭제 중 오류 발생:', error);
        alert('게시글 삭제 중 오류 발생: ' + error.message); // 오류 메시지를 더 명확하게 표시
    });
}


/* 댓글 수정 모드 활성화 */
function updateComment(commentId) {
    const contentSpan = document.getElementById(`content-${commentId}`);
    const textarea = document.getElementById(`textarea-${commentId}`);
    const submitButton = document.getElementById(`submit-${commentId}`);
    const updateButton = document.getElementById(`update-${commentId}`);

    textarea.value = contentSpan.innerText; //textarea <- 기존 content 넣기
    contentSpan.style.display = 'none'; //댓글 내용 숨김
    textarea.style.display = 'block'; //textarea 보이기
    submitButton.style.display = 'block'; //submitButton 보이기
    updateButton.style.display = 'none'; //updateButton 숨김
}


/* 댓글 수정 기능 수행 */
function submitComment(commentId) {
    console.log("submitComment called with ID:", commentId);  // 로그 추가
    const textarea = document.getElementById(`textarea-${commentId}`);
    const newText = textarea.value; //수정된 내용 가져오기

    //요청 페이로드 준비
    const data = JSON.stringify({
        id: commentId,
        content: newText
    });

    fetch('/api/comments/' + commentId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: data
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update comment');
            }
            if (response.headers.get('Content-Type').includes('application/json')) {
                return response.json();
            } else {
                throw new Error('Invalid response type');
            }
        })
        .then(data => {
            alert('댓글이 수정되었습니다: ' + data.message);
            const contentSpan = document.getElementById(`content-${commentId}`);
            contentSpan.innerText = newText;
            contentSpan.style.display = 'block';
            textarea.style.display = 'none';
            document.getElementById(`submit-${commentId}`).style.display = 'none'; // '게시' 버튼 숨기기
            document.getElementById(`update-${commentId}`).style.display = 'block'; // '수정' 버튼 보이기
        })
        .catch(error => {
            console.error('Error:', error);
            alert('댓글 수정 중 오류 발생: ' + error.message);
        });
}



/* 댓글 삭제 기능 수행 */
function deleteComment(commentId) {
    fetch('/api/comments/' + commentId, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(response => {
        if (!response.ok) {
            throw new Error('네트워크 응답이 실패했습니다.');
        }
        return response.text(); // 응답을 텍스트로 처리
    }).then(message => {
        alert(message); // 서버로부터 받은 메시지를 직접 출력
        location.reload(); //현재 페이지 새로고침
    }).catch(error => {
        console.error('댓글 삭제 중 오류 발생:', error);
        alert('댓글 삭제 중 오류 발생: ' + error.message); // 오류 메시지를 더 명확하게 표시
    });
}


/* 회원 삭제(탈퇴) 기능 수행 */
function deleteMember(memberId) {
    fetch('/api/members/' + memberId, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(response => {
        if (!response.ok) {
            throw new Error('네트워크 응답이 실패했습니다.');
        }
        return response.text(); // 응답을 텍스트로 처리
    }).then(message => {
        alert(message); // 서버로부터 받은 메시지를 직접 출력
        window.location.href = '/'; //메인 페이지로 이동
    }).catch(error => {
        console.error('회원 삭제 중 오류 발생:', error);
        alert('회원 삭제 중 오류 발생: ' + error.message); // 오류 메시지를 더 명확하게 표시
    });
}


/* 회원 정보 수정 기능 수행 */


