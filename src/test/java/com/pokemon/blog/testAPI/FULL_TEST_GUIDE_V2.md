# 📋 FULL TEST GUIDE — Blog Pokemon API v2.0

> **Base URL:** `http://localhost:8080`
> **Lưu ý:** Xóa database trước khi test (DROP + CREATE lại) để tránh trùng dữ liệu cũ

---

## 🔧 CHUẨN BỊ

```sql
DROP DATABASE pokemon_blog;
CREATE DATABASE pokemon_blog;
```

Restart Spring Boot app trước khi bắt đầu.

---

## 🔐 PHẦN 1: USER & AUTHENTICATION

### 1.1 Tạo Admin
```
POST /users
{
    "name": "Professor Oak",
    "userName": "oak",
    "userPassword": "admin123",
    "role": "ADMIN"
}
Expected: 200 → ghi lại token sau khi login
```

### 1.2 Tạo User thường (Ash)
```
POST /users
{
    "name": "Ash Ketchum",
    "userName": "ash",
    "userPassword": "pikachu123",
    "role": "USER"
}
Expected: 200
```

### 1.3 Tạo User thường (Misty)
```
POST /users
{
    "name": "Misty",
    "userName": "misty",
    "userPassword": "goldeen123",
    "role": "USER"
}
Expected: 200
```

### 1.4 ❌ Validation: Username quá ngắn/sai format
```
POST /users
{
    "name": "Test User",
    "userName": "a!",
    "userPassword": "test123",
    "role": "USER"
}
Expected: 400
Message: "Username chỉ được chứa chữ cái, số, dấu gạch dưới, dấu gạch ngang (3-50 ký tự)"
```

### 1.5 ❌ Validation: Password quá yếu
```
POST /users
{
    "name": "Test User",
    "userName": "testuser",
    "userPassword": "123",
    "role": "USER"
}
Expected: 400
Message: "Password tối thiểu 6 ký tự"
```

### 1.6 ❌ Duplicate: Username đã tồn tại
```
POST /users
{
    "name": "Ash Copy",
    "userName": "ash",
    "userPassword": "test123",
    "role": "USER"
}
Expected: 409
Message: "Username đã tồn tại"
```

### 1.7 Đăng nhập Admin
```
POST /auth/login
{
    "userName": "oak",
    "userPassword": "admin123"
}
Expected: 200
💾 TOKEN_ADMIN = response.token
```

### 1.8 Đăng nhập Ash
```
POST /auth/login
{
    "userName": "ash",
    "userPassword": "pikachu123"
}
Expected: 200
💾 TOKEN_ASH = response.token
```

### 1.9 Đăng nhập Misty
```
POST /auth/login
{
    "userName": "misty",
    "userPassword": "goldeen123"
}
Expected: 200
💾 TOKEN_MISTY = response.token
```

### 1.10 ❌ Đăng nhập sai password
```
POST /auth/login
{
    "userName": "ash",
    "userPassword": "wrongpassword"
}
Expected: 401
Message: "Mật khẩu không đúng"
```

### 1.11 ❌ Đăng nhập username không tồn tại
```
POST /auth/login
{
    "userName": "nonexistent",
    "userPassword": "test123"
}
Expected: 404
Message: "Không tìm thấy user: nonexistent"
```

### 1.12 Lấy danh sách users
```
GET /users
Expected: 200 → mảng 3 users
```

### 1.13 Lấy chi tiết 1 user
```
GET /users/1
Expected: 200
```

### 1.14 ❌ User không tồn tại
```
GET /users/9999
Expected: 404
Message: "Không tìm thấy User với id 9999"
```

---

## 🎮 PHẦN 2: POKEMON

### 2.1 Admin tạo Pokemon (Pikachu)
```
POST /pokemons
Authorization: Bearer TOKEN_ADMIN
{
    "name": "Pikachu",
    "type": "Electric",
    "description": "Pikachu là pokemon điện nổi tiếng nhất",
    "hp": 35,
    "attack": 55,
    "defense": 40,
    "specialAttack": 50,
    "specialDefense": 50,
    "speed": 90
}
Expected: 200 → POKEMON_ID_1 = 1
```

### 2.2 Admin tạo Pokemon (Charizard)
```
POST /pokemons
Authorization: Bearer TOKEN_ADMIN
{
    "name": "Charizard",
    "type": "Fire",
    "description": "Dragon-like pokemon rất mạnh",
    "hp": 78,
    "attack": 84,
    "defense": 78,
    "specialAttack": 109,
    "specialDefense": 85,
    "speed": 100
}
Expected: 200 → POKEMON_ID_2 = 2
```

### 2.3 ❌ User thường cố tạo Pokemon
```
POST /pokemons
Authorization: Bearer TOKEN_ASH
{
    "name": "Blastoise",
    "type": "Water",
    "description": "Water pokemon",
    "hp": 79,
    "attack": 83,
    "defense": 100,
    "specialAttack": 85,
    "specialDefense": 105,
    "speed": 78
}
Expected: 403
Message: "Chỉ Admin mới có quyền thêm Pokemon"
```

### 2.4 ❌ Validation: Name quá ngắn
```
POST /pokemons
Authorization: Bearer TOKEN_ADMIN
{
    "name": "P",
    "type": "Electric",
    "description": "Test",
    "hp": 35, "attack": 55, "defense": 40,
    "specialAttack": 50, "specialDefense": 50, "speed": 90
}
Expected: 400
Message: "Pokemon name phải từ 2 đến 50 ký tự"
```

### 2.5 ❌ Validation: Stats tổng vượt quá 600
```
POST /pokemons
Authorization: Bearer TOKEN_ADMIN
{
    "name": "Mewtwo Fake",
    "type": "Psychic",
    "description": "Overpowered pokemon",
    "hp": 200, "attack": 200, "defense": 200,
    "specialAttack": 200, "specialDefense": 200, "speed": 200
}
Expected: 400
Message: "Mỗi stat phải trong range 1-255 và tổng stats <= 600"
```

### 2.6 ❌ Validation: Stat = 0 (dưới min)
```
POST /pokemons
Authorization: Bearer TOKEN_ADMIN
{
    "name": "Weak Pokemon",
    "type": "Normal",
    "description": "Test",
    "hp": 0, "attack": 55, "defense": 40,
    "specialAttack": 50, "specialDefense": 50, "speed": 90
}
Expected: 400 (bắt bởi @Min annotation)
Message: "hp: HP phải lớn hơn 0"
```

### 2.7 ❌ Duplicate: Pokemon đã tồn tại
```
POST /pokemons
Authorization: Bearer TOKEN_ADMIN
{
    "name": "Pikachu",
    "type": "Electric",
    "description": "Duplicate test",
    "hp": 35, "attack": 55, "defense": 40,
    "specialAttack": 50, "specialDefense": 50, "speed": 90
}
Expected: 409
Message: "Pokemon Pikachu đã tồn tại"
```

### 2.8 Xem tất cả Pokemon (không cần token)
```
GET /pokemons
Expected: 200 → mảng chứa Pikachu, Charizard
```

### 2.9 Xem chi tiết Pokemon
```
GET /pokemons/1
Expected: 200
```

### 2.10 ❌ Pokemon không tồn tại
```
GET /pokemons/9999
Expected: 404
Message: "Không tìm thấy Pokemon với id 9999"
```

---

## 📝 PHẦN 3: POST (Blog)

### 3.1 Ash viết blog
```
POST /posts
Authorization: Bearer TOKEN_ASH
{
    "title": "Pikachu Training Guide",
    "content": "Pikachu là một trong những pokemon điện mạnh nhất, có khả năng phát điện cao áp"
}
Expected: 200 → POST_ID_1 = 1
```

### 3.2 Misty viết blog
```
POST /posts
Authorization: Bearer TOKEN_MISTY
{
    "title": "Water Pokemon Collection",
    "content": "Water pokemon rất mạnh mẽ, đặc biệt là Blastoise với khả năng phun nước"
}
Expected: 200 → POST_ID_2 = 2
```

### 3.3 Ash viết blog thứ 2 (để test search)
```
POST /posts
Authorization: Bearer TOKEN_ASH
{
    "title": "Electric vs Water Battle",
    "content": "Pikachu vs Blastoise, ai sẽ thắng trong trận chiến này?"
}
Expected: 200 → POST_ID_3 = 3
```

### 3.4 ❌ Validation: Title quá ngắn
```
POST /posts
Authorization: Bearer TOKEN_ASH
{
    "title": "Hi",
    "content": "Content dai du dai nhung title qua ngan"
}
Expected: 400
Message: "Title phải từ 5 đến 200 ký tự"
```

### 3.5 ❌ Validation: Content quá ngắn
```
POST /posts
Authorization: Bearer TOKEN_ASH
{
    "title": "Valid Title Here",
    "content": "short"
}
Expected: 400 (bắt bởi @Size annotation)
Message: "content: Content tối thiểu 10 ký tự"
```

### 3.6 ❌ Validation: Content chứa từ cấm
```
POST /posts
Authorization: Bearer TOKEN_ASH
{
    "title": "Bad Content Test",
    "content": "This is spam content that should be rejected"
}
Expected: 400
Message: "Content chứa từ không được phép"
```

### 3.7 ❌ Chưa đăng nhập (không có token)
```
POST /posts
{
    "title": "No Auth Test",
    "content": "Testing without authorization token"
}
Expected: 401 hoặc 403 (tùy Security config)
```

### 3.8 Xem tất cả posts (không cần token)
```
GET /posts
Expected: 200 → mảng 3 posts
```

### 3.9 Xem chi tiết post
```
GET /posts/1
Expected: 200
```

### 3.10 ❌ Post không tồn tại
```
GET /posts/9999
Expected: 404
Message: "Không tìm thấy Post với id 9999"
```

### 3.11 Ash sửa post của mình
```
PUT /posts/1
Authorization: Bearer TOKEN_ASH
{
    "title": "Pikachu Training Guide v2",
    "content": "Updated: Pikachu là pokemon điện mạnh nhất với khả năng đặc biệt"
}
Expected: 200
```

### 3.12 ❌ Misty cố sửa post của Ash
```
PUT /posts/1
Authorization: Bearer TOKEN_MISTY
{
    "title": "Hacked Title",
    "content": "This should not be allowed to update"
}
Expected: 403
Message: "Bạn không có quyền sửa post này"
```

### 3.13 ❌ Misty cố xóa post của Ash
```
DELETE /posts/1
Authorization: Bearer TOKEN_MISTY
Expected: 403
Message: "Bạn không có quyền xóa post này"
```

### 3.14 Admin xóa post của bất kỳ ai
```
DELETE /posts/2
Authorization: Bearer TOKEN_ADMIN
Expected: 204 No Content
```

---

## 🔍 PHẦN 4: SEARCH

### 4.1 Search theo "pikachu"
```
GET /posts/search?keyword=pikachu
Expected: 200 → mảng chứa post 1 và post 3
```

### 4.2 Search theo "water" (post đã bị xóa)
```
GET /posts/search?keyword=water
Expected: 200 → mảng rỗng (vì post 2 đã xóa ở bước 3.14)
```

### 4.3 Search không tìm thấy
```
GET /posts/search?keyword=nonexistentword
Expected: 200 → mảng rỗng []
```

### 4.4 ❌ Validation: Keyword quá ngắn
```
GET /posts/search?keyword=a
Expected: 400
Message: "Keyword phải từ 2 đến 100 ký tự"
```

### 4.5 ❌ Validation: Keyword rỗng
```
GET /posts/search?keyword=
Expected: 400
Message: "Keyword phải từ 2 đến 100 ký tự"
```

### 4.6 ❌ Validation: Keyword quá dài (>100 ký tự)
```
GET /posts/search?keyword=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
Expected: 400
Message: "Keyword phải từ 2 đến 100 ký tự"
```

---

## 👍 PHẦN 5: LIKE

### 5.1 Ash like post của Misty (post 3 - của Ash chính mình cũng được)
```
POST /posts/3/likes
Authorization: Bearer TOKEN_MISTY
Expected: 200
```

### 5.2 Admin like post 3
```
POST /posts/3/likes
Authorization: Bearer TOKEN_ADMIN
Expected: 200
```

### 5.3 ❌ Misty like lại post 3 (đã like rồi)
```
POST /posts/3/likes
Authorization: Bearer TOKEN_MISTY
Expected: 409
Message: "Bạn đã like post này rồi"
```

### 5.4 Xem số like
```
GET /posts/3/likes
Expected: 200 → 2
```

### 5.5 ❌ Like post không tồn tại
```
POST /posts/9999/likes
Authorization: Bearer TOKEN_ASH
Expected: 404
Message: "Không tìm thấy Post với id 9999"
```

### 5.6 Misty bỏ like
```
DELETE /posts/3/likes
Authorization: Bearer TOKEN_MISTY
Expected: 204 No Content
```

### 5.7 ❌ Bỏ like khi chưa like
```
DELETE /posts/3/likes
Authorization: Bearer TOKEN_MISTY
Expected: 404
Message: "Bạn chưa like post này"
```

### 5.8 Xem lại số like
```
GET /posts/3/likes
Expected: 200 → 1
```

---

## 💬 PHẦN 6: COMMENT

### 6.1 Ash comment vào post 3
```
POST /posts/3/comments
Authorization: Bearer TOKEN_ASH
{
    "content": "Bài viết rất hay, mình thích trận đấu này!"
}
Expected: 200 → COMMENT_ID_1 = 1
```

### 6.2 Misty comment vào post 3
```
POST /posts/3/comments
Authorization: Bearer TOKEN_MISTY
{
    "content": "Cảm ơn bạn đã chia sẻ!"
}
Expected: 200 → COMMENT_ID_2 = 2
```

### 6.3 ❌ Validation: Comment rỗng
```
POST /posts/3/comments
Authorization: Bearer TOKEN_ASH
{
    "content": ""
}
Expected: 400
Message: "Nội dung comment không được trống" hoặc "Comment phải từ 1 đến 1000 ký tự"
```

### 6.4 ❌ Comment vào post không tồn tại
```
POST /posts/9999/comments
Authorization: Bearer TOKEN_ASH
{
    "content": "Test comment"
}
Expected: 404
Message: "Không tìm thấy Post với id 9999"
```

### 6.5 Xem tất cả comment của post
```
GET /posts/3/comments
Expected: 200 → mảng 2 comments
```

### 6.6 Ash sửa comment của mình
```
PUT /posts/3/comments/1
Authorization: Bearer TOKEN_ASH
{
    "content": "Updated: Bài viết rất hay, trận đấu quá gay cấn!"
}
Expected: 200
```

### 6.7 ❌ Misty cố sửa comment của Ash
```
PUT /posts/3/comments/1
Authorization: Bearer TOKEN_MISTY
{
    "content": "Hack attempt"
}
Expected: 403
Message: "Bạn không có quyền sửa comment này"
```

### 6.8 ❌ Sửa comment không tồn tại
```
PUT /posts/3/comments/9999
Authorization: Bearer TOKEN_ASH
{
    "content": "Test"
}
Expected: 404
Message: "Không tìm thấy Comment với id 9999"
```

### 6.9 Ash xóa comment của mình
```
DELETE /posts/3/comments/1
Authorization: Bearer TOKEN_ASH
Expected: 204 No Content
```

### 6.10 Admin xóa comment của Misty
```
DELETE /posts/3/comments/2
Authorization: Bearer TOKEN_ADMIN
Expected: 204 No Content
```

### 6.11 ❌ Xóa comment không tồn tại
```
DELETE /posts/3/comments/9999
Authorization: Bearer TOKEN_ASH
Expected: 404
Message: "Không tìm thấy Comment với id 9999"
```

---

## 🌐 PHẦN 7: EDGE CASES & ERROR HANDLING

### 7.1 ❌ JSON format sai
```
POST /posts
Authorization: Bearer TOKEN_ASH
Content-Type: application/json

{
    "title": "Test
    "content": missing quotes and comma
}
Expected: 400
Message: "JSON format không hợp lệ. Kiểm tra lại request body"
```

### 7.2 ❌ Path variable sai kiểu dữ liệu
```
GET /posts/abc
Expected: 400
Message: "Giá trị không hợp lệ cho parameter 'id': abc"
```

### 7.3 ❌ Token hết hạn / không hợp lệ
```
GET /posts/search?keyword=test
Authorization: Bearer invalid_token_12345
Expected: Vẫn trả 200 vì GET /posts/search không cần auth
```

```
POST /posts
Authorization: Bearer invalid_token_12345
{
    "title": "Valid Title",
    "content": "Valid content over 10 chars"
}
Expected: 401/403 - token không hợp lệ, không set được authentication
```

### 7.4 ❌ Thiếu Authorization header khi cần
```
DELETE /posts/1
(không có Authorization header)
Expected: 401 hoặc 403
```

### 7.5 ❌ Foreign key violation (nếu test trực tiếp DB)
```
Note: Không thể test qua API vì đã dùng SecurityContextHolder
Đây là edge case đã được xử lý ở tầng Service
```

---

## ✅ CHECKLIST TỔNG QUÁT

### Authentication & Authorization
- [ ] Đăng ký user thành công
- [ ] Đăng ký duplicate username → 409
- [ ] Đăng nhập thành công → nhận token
- [ ] Đăng nhập sai password → 401
- [ ] Đăng nhập username không tồn tại → 404
- [ ] Gọi API cần auth mà không có token → 401/403
- [ ] Token không hợp lệ → 401/403

### CRUD Operations
- [ ] Create/Read/Update/Delete Post hoạt động đúng
- [ ] Create/Read Pokemon hoạt động đúng (chỉ Admin tạo được)
- [ ] Create/Read/Update/Delete Comment hoạt động đúng
- [ ] Like/Unlike hoạt động đúng

### Authorization Rules
- [ ] Chỉ chủ Post mới sửa/xóa được (trừ Admin)
- [ ] Chỉ chủ Comment mới sửa được (trừ Admin xóa được)
- [ ] Chỉ Admin mới tạo được Pokemon
- [ ] User thường cố làm việc của Admin → 403

### Business Validation
- [ ] Username format (chữ, số, gạch dưới/ngang, 3-50 ký tự)
- [ ] Password tối thiểu 6 ký tự
- [ ] Post title 5-200 ký tự
- [ ] Post content không chứa từ cấm
- [ ] Pokemon name 2-50 ký tự
- [ ] Pokemon stats mỗi cái 1-255, tổng <= 600
- [ ] Comment 1-1000 ký tự
- [ ] Search keyword 2-100 ký tự

### Exception Handling
- [ ] 404 khi resource không tồn tại
- [ ] 409 khi duplicate resource
- [ ] 403 khi không có quyền
- [ ] 401 khi sai credentials
- [ ] 400 khi validation fail
- [ ] 400 khi JSON format sai
- [ ] 500 không lộ stack trace ra ngoài

---

## 📊 Bảng tổng hợp HTTP Status Codes

| Status | Khi nào xảy ra |
|---|---|
| 200 | Request thành công (GET, POST tạo mới, PUT update) |
| 204 | Request thành công, không có response body (DELETE) |
| 400 | Validation lỗi, JSON sai format, business rule vi phạm |
| 401 | Sai credentials, token không hợp lệ |
| 403 | Không có quyền thực hiện hành động |
| 404 | Resource không tồn tại |
| 409 | Dữ liệu bị trùng (duplicate) |
| 500 | Lỗi server không lường trước |

---

## 🎯 Sau khi test xong

Nếu **tất cả checklist đều pass** → Backend đã sẵn sàng:
1. ✅ Đưa vào CV thực tập
2. ✅ Viết README.md giới thiệu project
3. ✅ Push lên GitHub
4. ✅ Bắt đầu làm Frontend (nếu muốn)

**Chúc mừng em đã hoàn thành một Backend project chuẩn production! 🎉**
